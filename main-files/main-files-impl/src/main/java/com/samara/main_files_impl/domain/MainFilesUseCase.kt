package com.samara.main_files_impl.domain

import android.content.Context
import android.net.Uri
import android.os.Parcelable
import androidx.core.content.FileProvider
import com.samara.main_files_api.data.IFilesRepo
import com.samara.main_files_api.domain.models.FileDomain
import com.samara.main_files_api.domain.useCase.IMainFilesUseCase
import di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import java.io.File
import javax.inject.Inject


class MainFilesUseCase @Inject constructor(
    private val filesRepo: IFilesRepo,
    private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IMainFilesUseCase {

    @Parcelize
    data class FilesState(
        override val listFiles: List<FileDomain> = emptyList(),
        override val depthNumber: Long = 0L,
        override val currentPath: String = "",
        override val loading: Boolean = false
    ) : IMainFilesUseCase.State, Parcelable

    private val _filesState: MutableStateFlow<IMainFilesUseCase.State> = MutableStateFlow(
        FilesState()
    )
    override val filesState: StateFlow<IMainFilesUseCase.State>
        get() = _filesState.asStateFlow()

    private val actualState: FilesState
        get() = filesState.value as FilesState


    @OptIn(ExperimentalCoroutinesApi::class)
    private val innerDispatcher = Dispatchers.IO.limitedParallelism(1)
    private val coroutineContext = innerDispatcher + SupervisorJob()
    private val innerScope: CoroutineScope = CoroutineScope(coroutineContext)

    private fun launchWork(fileWork: suspend () -> FilesState) {
        innerScope.launch {
            _filesState.emit(
                fileWork.invoke()
            )
        }
    }

    private suspend fun getFiles(absolutePath: String? = null): List<FileDomain> = withContext(ioDispatcher) {
        val files = filesRepo.getFiles(absolutePath).map {
            FileDomain(it.absolutePath, it.name, it.isDirectory, it.extension)
        }

        return@withContext files.sortedWith(compareBy({ !it.isDir }, { it.title }))
    }

    private suspend fun getInitialPath(): String = filesRepo.getInitialPath()

    override fun openDir(path: String?) = launchWork {
        val result = getFiles(path)

        actualState.copy(
            listFiles = result,
            depthNumber = actualState.depthNumber + 1,
            currentPath = path ?: ""
        )
    }

    override fun initRoot() = launchWork {
        actualState.copy(
            listFiles = getFiles(),
            currentPath = getInitialPath(),
            loading = false
        )
    }

    override fun backAction() = launchWork {
        val currentPath = actualState.currentPath
        val newPath = currentPath.split(File.separator).toList().dropLast(1).joinToString(File.separator)

        val result = getFiles(newPath)
        actualState.copy(
            listFiles = result,
            depthNumber = actualState.depthNumber - 1,
            currentPath = newPath,
        )
    }

    override fun onCleared() {
        this.coroutineContext.cancelChildren()
    }

    override fun setInitState(
        listFiles: List<FileDomain>,
        depthNumber: Long,
        currentPath: String,
        loading: Boolean
    ) = launchWork {
        actualState.copy(
            listFiles = listFiles,
            currentPath = currentPath,
            depthNumber = depthNumber,
            loading = loading
        )
    }

    override fun convertPathToUri(path: String): Uri {
        return FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", File(path));
    }
}