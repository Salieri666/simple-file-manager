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

/**
 * private static boolean isValidFatFilenameChar(char c) {
if ((0x00 <= c && c <= 0x1f)) {
return false;
}
switch (c) {
case '"':
case '*':
case '/':
case ':':
case '<':
case '>':
case '?':
case '\\':
case '|':
case 0x7F:
return false;
default:
return true;
}
}

private static boolean isValidExtFilenameChar(char c) {
switch (c) {
case '\0':
case '/':
return false;
default:
return true;
}
}
private static final String ReservedChars = "|\\?*<\":>+[]/'";

 ограничение в 60 символов
 */