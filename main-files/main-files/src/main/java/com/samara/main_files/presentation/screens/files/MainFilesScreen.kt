package com.samara.main_files.presentation.screens.files

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.samara.main_files.presentation.component.BottomSheetChooseFileType
import com.samara.main_files.presentation.mappers.FileExtensions
import com.samara.main_files.presentation.models.FileUi
import com.samara.main_files.presentation.navigation.IMainFilesNavigation
import components.FileComponent
import extensions.openFile
import kotlinx.coroutines.launch
import models.UiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainFilesScreen(
    vm: MainFilesVm,
    navigation: IMainFilesNavigation,
    modifier: Modifier = Modifier
) {

    val state by vm.state.collectAsState()
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    LaunchedEffect(true) {
        vm.dispatch(MainFilesAction.InitRoot)
    }

    //for bottom sheet
    var path by remember {
        mutableStateOf<Uri?>(null)
    }
    LaunchedEffect(true) {
        vm.effects.collect { e ->
            when (e) {
                is MainFilesEffect.OpenFile -> {

                    if (e.ext != FileExtensions.UNKNOWN)
                        context.openFile(e.path, e.ext.mime)
                    else {
                        path = e.path
                        scope.launch {
                            sheetState.show()
                        }
                    }

                }
            }
        }
    }



    BackHandler(state.depthNumber > 0) {
        vm.dispatch(MainFilesAction.BackAction)
    }


    ModalBottomSheetLayout(sheetState = sheetState, sheetContent = {
        BottomSheetChooseFileType(
            elements = state.nameTypeFilesList,
            modifier = Modifier.padding(16.dp),
            onClick = { item: FileExtensions ->
                path?.let { context.openFile(it, item.mime) }

                scope.launch {
                    sheetState.hide()
                    path = null
                }
            }
        )
    }) {
        MainFilesScreen(
            files = state.files,
            uiState = state.uiState,
            modifier = modifier,
            onClick = { file ->
                if (file.isDir) vm.dispatch(MainFilesAction.OpenDir(file.absolutePath))
                else vm.dispatch(MainFilesAction.OpenFile(file.absolutePath, file.ext))
            }
        )
    }

}

@Composable
fun MainFilesScreen(
    files: List<FileUi>,
    uiState: UiState,
    modifier: Modifier = Modifier,
    onClick: (FileUi) -> Unit
) {

    when (uiState) {
        UiState.Success ->
            if (files.isEmpty()) {
                Text(text = "Folder is empty")
            } else {

                LazyColumn(
                    modifier = modifier
                ) {

                    items(files, key = { it.absolutePath }) {
                        it.apply {
                            FileComponent(
                                title = title,
                                isDir = isDir,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { onClick(it) }
                            )
                        }
                    }

                }
            }

        else -> {}
    }
}