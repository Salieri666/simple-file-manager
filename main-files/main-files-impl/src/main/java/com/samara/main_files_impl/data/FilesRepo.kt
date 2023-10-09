package com.samara.main_files_impl.data

import android.os.Environment
import android.util.Log
import com.samara.main_files_api.data.IFilesRepo
import di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import kotlin.io.path.Path
import kotlin.io.path.deleteIfExists

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

    override suspend fun deleteFiles(paths: List<String>) = withContext(ioDispatcher) {
        for (el: String in paths) {
            val file = Path(el)
            file.deleteIfExists()
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
}