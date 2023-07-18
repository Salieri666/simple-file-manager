package com.samara.main_files.presentation.screens.files

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.samara.main_files.presentation.models.FileUi
import com.samara.main_files.presentation.navigation.IMainFilesNavigation
import components.FileComponent

@Composable
fun MainFilesScreen(
    vm: MainFilesVm,
    navigation: IMainFilesNavigation,
    modifier: Modifier = Modifier
) {

    val state by vm.state.collectAsState()

    LaunchedEffect(true) {
        vm.dispatch(MainFilesAction.InitRoot)
    }

    val context = LocalContext.current

    BackHandler(state.depthNumber > 0) {
        vm.dispatch(MainFilesAction.BackAction)
    }

    MainFilesScreen(
        files = state.files,
        modifier = modifier,
        onClick = { isDir, path ->
            if (isDir) vm.dispatch(MainFilesAction.OpenDir(path))
        }
    )
}

@Composable
fun MainFilesScreen(
    files: List<FileUi>,
    modifier: Modifier = Modifier,
    onClick: (Boolean, String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {

        items(files, key = { it.absolutePath }) {
            it.apply {
                FileComponent(
                    title = title,
                    isDir = isDir,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { isDir -> onClick(isDir, it.absolutePath) }
                )
            }
        }

    }
}