package com.samara.main_files.presentation.models

import android.os.Parcelable
import com.samara.main_files.presentation.mappers.FileExtensions
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileUi(
    val absolutePath: String,
    val title: String,
    val isDir: Boolean,
    val ext: FileExtensions,
    val extStr: String,
    val isChecked: Boolean = false
): Parcelable
