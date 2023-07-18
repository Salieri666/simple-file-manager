package com.samara.main_files_api.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileDomain(
    val absolutePath: String,
    val title: String,
    val isDir: Boolean
): Parcelable
