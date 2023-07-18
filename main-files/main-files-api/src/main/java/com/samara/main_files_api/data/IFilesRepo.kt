package com.samara.main_files_api.data

import java.io.File

interface IFilesRepo {
    suspend fun getFiles(absolutePath: String?): List<File>

    suspend fun getInitialPath(): String

    //suspend fun getFileSeparator(): String = File.separator
}