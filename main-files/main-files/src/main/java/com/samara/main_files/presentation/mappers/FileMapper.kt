package com.samara.main_files.presentation.mappers

import androidx.annotation.DrawableRes
import com.samara.main_files.presentation.models.FileUi
import com.samara.main_files_api.domain.models.FileDomain

fun FileDomain.toFileUI() = FileUi(
    absolutePath = absolutePath,
    title = title,
    isDir = isDir,
    ext = convertStringExt(fileExtension)
)

fun FileUi.toFileDomain() = FileDomain(
    absolutePath = absolutePath,
    title = title,
    isDir = isDir,
    fileExtension = ext.ext
)

enum class FileExtensions(
    val ext: String,
    val mime: String,
    @DrawableRes val drawable: Int
) {
    PDF("pdf", "application/pdf", 0),

    UNKNOWN("","",0)
}

fun convertStringExt(ext: String): FileExtensions {
    return when(ext) {
        "pdf" -> FileExtensions.PDF
        else -> FileExtensions.UNKNOWN
    }
}
