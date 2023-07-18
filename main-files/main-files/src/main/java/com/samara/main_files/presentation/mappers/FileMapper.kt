package com.samara.main_files.presentation.mappers

import com.samara.main_files.presentation.models.FileUi
import com.samara.main_files_api.domain.models.FileDomain

fun FileDomain.toFileUI() = FileUi(
    absolutePath = absolutePath,
    title = title,
    isDir = isDir
)

fun FileUi.toFileDomain() = FileDomain(
    absolutePath = absolutePath,
    title = title,
    isDir = isDir
)

