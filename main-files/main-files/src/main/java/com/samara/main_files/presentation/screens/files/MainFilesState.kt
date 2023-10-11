package com.samara.main_files.presentation.screens.files

import com.samara.main_files.presentation.mappers.FileExtensions
import com.samara.main_files.presentation.models.FileUi
import kotlinx.parcelize.Parcelize
import models.UiState
import presentation.base.State

@Parcelize
data class MainFilesState(
    val uiState: UiState = UiState.Loading,
    val isInitialized: Boolean = false,
    val depthNumber: Long = 0L,
    val currentPath: String? = "",
    val files: List<FileUi> = listOf(),
    val nameTypeFilesList: List<FileExtensions> = getNameTypeFilesList(),
    val editMode: Boolean = false,
    val selectedItemsText: String = "",
    val openDialogDetails: Boolean = false,
    val openRenameDialog: Boolean = false,
    val textForRename: String = "",
    val countsDialogDetails: Int = 0,
    val size: String = "",
    val lastChanged: String = "",
    val showInvalidTitle: Boolean = false,
    val showDuplicateMsg: Boolean = false,
) : State

fun getNameTypeFilesList(): List<FileExtensions> {
    return arrayListOf(FileExtensions.TEXT, FileExtensions.AUDIO, FileExtensions.IMAGE, FileExtensions.VIDEO)
}
