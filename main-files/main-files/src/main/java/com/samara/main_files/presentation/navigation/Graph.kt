package com.samara.main_files.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.samara.main_files.di.MainFilesComponentHolder
import com.samara.main_files.presentation.screens.files.MainFilesScreen
import extensions.getVm
import presentation.navigation.Destination


fun NavGraphBuilder.mainFilesGraph(
    navigation: IMainFilesNavigation,
    before: @Composable (NavBackStackEntry.(Destination) -> Unit)? = null
) {
    composable(MainFilesDestination.route) { navEntry ->
        before?.also { navEntry.before(MainFilesDestination) }
        MainFilesScreen(
            vm = navEntry.getVm(MainFilesComponentHolder.get().mainFilesVmFactory()),
            navigation = navigation
        )
    }
}