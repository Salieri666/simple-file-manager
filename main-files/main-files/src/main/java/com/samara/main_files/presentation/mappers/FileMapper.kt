package com.samara.main_files.presentation.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.samara.main_files.R
import com.samara.main_files.presentation.models.FileUi
import com.samara.main_files_api.domain.models.FileDomain

fun FileDomain.toFileUI() = FileUi(
    absolutePath = absolutePath,
    title = title,
    isDir = isDir,
    ext = convertStringExt(fileExtension),
    extStr = fileExtension
)

fun FileUi.toFileDomain() = FileDomain(
    absolutePath = absolutePath,
    title = title,
    isDir = isDir,
    fileExtension = extStr
)

enum class FileExtensions(
    val ext: List<String>,
    val mime: String,
    @DrawableRes val drawable: Int,
    @StringRes val nameRes: Int
) {
    PDF(arrayListOf("pdf"), "application/pdf", 0, R.string.pdf),
    MSWORD(arrayListOf("doc", "dot"), "application/msword", 0, R.string.word),
    MSWORDX(arrayListOf("docx"), "application/vnd.openxmlformats-officedocument.wordprocessingml.document", 0, R.string.word),
    EXCEL(arrayListOf("xls", "xlb", "xlt"), "application/vnd.ms-excel", 0, R.string.excel),
    AUDIO(arrayListOf("mp3", "mpga", "mpega", "mp2", "mp3", "m4a", "wax"), "audio/*", 0, R.string.audio),
    VIDEO(arrayListOf("mp4", "webm", "fli", "avi", "mkv"), "video/*", 0, R.string.video),
    TEXT(arrayListOf("asc", "txt", "text", "pot", "brf", "srt"),"text/*", 0, R.string.text),
    IMAGE(arrayListOf("jpeg", "jpg", "jpe", "gif", "jp2", "jpg2", "jpm", "png", "svg", "svgz","tiff", "tif", "ico", "wbmp", "bmp"), "image/*", 0, R.string.image),


    UNKNOWN(emptyList(),"",0, R.string.unknown);
}

fun convertStringExt(ext: String): FileExtensions {
    val enums = FileExtensions.values()

    for (e in enums) {
        if (e.ext.contains(ext)) {
            return e
        }
    }

    return FileExtensions.UNKNOWN
}
