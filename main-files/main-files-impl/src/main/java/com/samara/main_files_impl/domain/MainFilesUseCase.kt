package com.samara.main_files_impl.domain

import android.content.Context
import android.net.Uri
import android.os.Parcelable
import androidx.core.content.FileProvider
import com.samara.main_files_api.data.IFilesRepo
import com.samara.main_files_api.domain.models.FileDomain
import com.samara.main_files_api.domain.useCase.IMainFilesUseCase
import com.samara.main_files_impl.R
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
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
        val files: List<FileDomain> = filesRepo.getFiles(absolutePath).map {
            val attr: BasicFileAttributes = Files.readAttributes(it.toPath(), BasicFileAttributes::class.java);

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a")
                .withZone(ZoneId.systemDefault())

            val changedTime = attr.lastModifiedTime().toInstant()
            val changedTimeString = formatter.format(changedTime)

            val filesCount = (it.listFiles()?.size ?: 0).toInt()
            val filesInDirectory = context.resources.getQuantityString(R.plurals.fileItems, filesCount, filesCount)


            val sizeString = humanReadableByteCountBin(attr.size())

            return@map FileDomain(
                it.absolutePath,
                it.name,
                it.isDirectory,
                it.extension,
                size = if (it.isDirectory) filesInDirectory else sizeString,
                sizeBytes = attr.size(),
                changedDate = changedTimeString
            )
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

    override fun humanReadableByteCountBin(bytes: Long): String {
        val absB = if (bytes == Long.MIN_VALUE) Long.MAX_VALUE else Math.abs(bytes)
        if (absB < 1024) {
            return "$bytes B"
        }
        var value = absB
        val ci: CharacterIterator = StringCharacterIterator("KMGTPE")
        var i = 40
        while (i >= 0 && absB > 0xfffccccccccccccL shr i) {
            value = value shr 10
            ci.next()
            i -= 10
        }
        value *= java.lang.Long.signum(bytes).toLong()
        return String.format("%.1f %ciB", value / 1024.0, ci.current())
    }

    override fun delete(listFiles: List<FileDomain>) = launchWork {
        filesRepo.deleteFiles(listFiles.map { it.absolutePath })

        val result = getFiles(actualState.currentPath)

        actualState.copy(
            listFiles = result,
            depthNumber = actualState.depthNumber + 1,
            currentPath = actualState.currentPath
        )
    }

    override fun checkTitleFile(title: String): Boolean {
        if (title.length >= 60) return false;

        for (c: Char in title) {
            if (!checkIsValidChar(c))
                return false
        }

        return true;
    }

    private fun checkIsValidChar(c: Char): Boolean {
        if ((0x00.toChar() <= c && c <= 0x1f.toChar())) {
            return false;
        }

        return when(c) {
            '"', '*', '/', ':', '<', '>', '?', '\\', '|', '[', ']', 0x7F.toChar() -> false
            else -> true
        }
    }

    override fun renameFile(newTitle: String, path: String) = launchWork {
        filesRepo.renameFile(newTitle, path)
        val result = getFiles(actualState.currentPath)

        actualState.copy(
            listFiles = result,
            depthNumber = actualState.depthNumber,
            currentPath = actualState.currentPath ?: ""
        )
    }

    override fun moveFiles(listFiles: List<FileDomain>) = launchWork {
        if (listFiles.isNotEmpty())
            filesRepo.moveFiles(listFiles.map { File(it.absolutePath) }, actualState.currentPath)

        val result = getFiles(actualState.currentPath)

        actualState.copy(
            listFiles = result,
            depthNumber = actualState.depthNumber,
            currentPath = actualState.currentPath ?: ""
        )
    }
}