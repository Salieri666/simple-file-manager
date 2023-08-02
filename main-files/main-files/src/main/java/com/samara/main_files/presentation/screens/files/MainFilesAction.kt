package com.samara.main_files.presentation.screens.files

import android.net.Uri
import com.samara.main_files.presentation.mappers.FileExtensions
import com.samara.main_files.presentation.models.FileUi
import com.samara.main_files_api.domain.models.FileDomain
import presentation.base.Action

sealed interface MainFilesAction : Action {
    object InitRoot : MainFilesAction
    object BackAction : MainFilesAction

    data class StateChangedAction(
        val listFiles: List<FileDomain>,
        val depthNumber: Long,
        val currentPath: String,
        val loading: Boolean,
    ) : MainFilesAction

    data class ToEditMode(
        val file: FileUi
    ) : MainFilesAction

    data class ClickOnElement(
        val file: FileUi
    ) : MainFilesAction
}

sealed interface MainFilesEffect {
    data class OpenFile(
        val path: Uri,
        val ext: FileExtensions
    ): MainFilesEffect
}

