package com.samara.main_files_impl.data

import android.os.Environment
import android.util.Log
import com.samara.main_files_api.data.IFilesRepo
import di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

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
}