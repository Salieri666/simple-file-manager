package com.samara.main_files.presentation.screens.files

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.samara.main_files.presentation.mappers.FileExtensions
import com.samara.main_files.presentation.models.FileUi
import com.samara.main_files.presentation.navigation.IMainFilesNavigation
import components.FileComponent
import extensions.openFile

@Composable
fun MainFilesScreen(
    vm: MainFilesVm,
    navigation: IMainFilesNavigation,
    modifier: Modifier = Modifier
) {

    val state by vm.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(true) {
        vm.dispatch(MainFilesAction.InitRoot)
    }

    LaunchedEffect(true) {
        vm.effects.collect { e ->
            when (e) {
                is MainFilesEffect.OpenFile -> {
                    if (e != FileExtensions.UNKNOWN)
                        context.openFile(e.path, e.ext.mime)
                }
            }
        }
    }



    BackHandler(state.depthNumber > 0) {
        vm.dispatch(MainFilesAction.BackAction)
    }

    MainFilesScreen(
        files = state.files,
        modifier = modifier,
        onClick = { file ->
            if (file.isDir) vm.dispatch(MainFilesAction.OpenDir(file.absolutePath))
            else vm.dispatch(MainFilesAction.OpenFile(file.absolutePath, file.ext))
        }
    )
}

@Composable
fun MainFilesScreen(
    files: List<FileUi>,
    modifier: Modifier = Modifier,
    onClick: (FileUi) -> Unit
) {
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
}