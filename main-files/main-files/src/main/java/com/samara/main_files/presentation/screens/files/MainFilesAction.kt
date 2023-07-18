package com.samara.main_files.presentation.screens.files

import com.samara.main_files_api.domain.models.FileDomain
import presentation.base.Action

sealed interface MainFilesAction : Action {
    data class OpenDir(
        val path: String?
    ): MainFilesAction

    object InitRoot : MainFilesAction
    object BackAction : MainFilesAction

    data class StateChangedAction(
        val listFiles: List<FileDomain>,
        val depthNumber: Long,
        val currentPath: String,
    ) : MainFilesAction
}