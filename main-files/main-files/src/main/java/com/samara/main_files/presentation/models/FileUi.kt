package com.samara.main_files.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileUi(
    val absolutePath: String,
    val title: String,
    val isDir: Boolean
): Parcelable
