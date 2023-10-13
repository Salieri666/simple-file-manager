package com.samara.main_files_impl.data

import android.os.Environment
import android.util.Log
import com.samara.main_files_api.data.IFilesRepo
import di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.deleteRecursively

class FilesRepo @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IFilesRepo {

    override suspend fun getFiles(absolutePath: String?): List<File> = withContext(ioDispatcher) {
        val path = if (absolutePath != null) File(absolutePath) else Environment.getExternalStorageDirectory()
        Log.d("Files", "Path: $path")

        val files = path.listFiles()
        Log.d("Files", "Size: " + files?.size)

        return@withContext files?.toList() ?: emptyList();
    }

    override suspend fun getInitialPath(): String = withContext(ioDispatcher) {
        return@withContext Environment.getExternalStorageDirectory().absolutePath
    }

    @OptIn(ExperimentalPathApi::class)
    override suspend fun deleteFiles(paths: List<String>) = withContext(ioDispatcher) {
        for (el: String in paths) {
            val file = Path(el)
            file.deleteRecursively()
        }
    }

    override suspend fun renameFile(newTitle: String, path: String) = withContext(ioDispatcher) {
        val oldFile = File(path)

        if (oldFile.exists()) {
            val newPath = path.split(File.separator).toMutableList()
            newPath[newPath.size - 1] = newTitle

            val newFile = File(newPath.joinToString(File.separator))
            val rename = oldFile.renameTo(newFile)
        }
    }

    override suspend fun moveFiles(files: List<File>, path: String) {
        for (file: File in files) {
            val newPath = file.absolutePath.split(File.separator).last()

            val destFile = File(path, newPath).apply {
                if (!parentFile.canWrite()) {
                    throw IOException("cannot write to $parent")
                } else if (exists() && !canWrite()) {
                    throw IOException("cannot write to ${this.absolutePath}")
                }
            }

            file.copyRecursively(
                target = destFile,
                onError = { _, exception ->  OnErrorAction.SKIP }
            )
        }
    }
}