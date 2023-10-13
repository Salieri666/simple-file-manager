package com.samara.main_files_api.data

import java.io.File

interface IFilesRepo {
    suspend fun getFiles(absolutePath: String?): List<File>

    suspend fun getInitialPath(): String

    suspend fun deleteFiles(paths: List<String>)

    suspend fun renameFile(newTitle: String, path: String)

    suspend fun moveFiles(files: List<File>, path: String)
}