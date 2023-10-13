package com.samara.main_files.presentation.screens.files

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.samara.main_files.R
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
    val filesForPasting: List<FileUi> = emptyList(),
    val nameTypeFilesList: List<FileExtensions> = getNameTypeFilesList(),
    val editMode: Boolean = false,
    val pasteMode: Boolean = false,
    val selectedItemsText: String = "",
    val openDialogDetails: Boolean = false,
    val openRenameDialog: Boolean = false,
    val textForRename: String = "",
    val countsDialogDetails: Int = 0,
    val size: String = "",
    val lastChanged: String = "",
    val showInvalidTitle: Boolean = false,
    val showDuplicateMsg: Boolean = false,
    val bottomActions: List<BottomFileAction> = getBottomFileActionsList()
) : State

fun getNameTypeFilesList(): List<FileExtensions> {
    return arrayListOf(FileExtensions.TEXT, FileExtensions.AUDIO, FileExtensions.IMAGE, FileExtensions.VIDEO)
}


fun getBottomFileActionsList(): List<BottomFileAction> {
    return arrayListOf(
        BottomFileAction(BottomFileActionType.COPY, R.drawable.move, R.string.copy, true),
        BottomFileAction(BottomFileActionType.DELETE, R.drawable.delete, R.string.delete, true),
        BottomFileAction(BottomFileActionType.RENAME, R.drawable.text_field, R.string.rename, true),
        BottomFileAction(BottomFileActionType.DETAIL, R.drawable.detail, R.string.details, true)
    )
}

fun getBottomActionsForPasting(): List<BottomFileAction> {
    return arrayListOf(
        BottomFileAction(BottomFileActionType.PASTE, R.drawable.paste, R.string.paste, true),
        BottomFileAction(BottomFileActionType.CANCEL, R.drawable.cancel_bottom, R.string.cancel, true)
    )
}


@Parcelize
data class BottomFileAction(
   val type: BottomFileActionType,
   @DrawableRes val icon: Int,
   @StringRes val title: Int,
   val isActive: Boolean,
   val isVisible: Boolean = true
) : Parcelable

enum class BottomFileActionType {
    COPY, DELETE, RENAME, DETAIL,
    PASTE, CANCEL //for MOVE action after click
}

