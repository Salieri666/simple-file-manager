package com.samara.main_files.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import presentation.navigation.IBasicNavigation

interface IMainFilesNavigation : IBasicNavigation {
    fun navigateToMainFiles()
}

@Stable
class MainFilesNavigation(basicNavigation: IBasicNavigation) : IMainFilesNavigation, IBasicNavigation by basicNavigation {

    override fun navigateToMainFiles() = navController.navigate(
        MainFilesDestination.route
    )

}

@Composable
fun rememberMainFilesNavigation(basicNavigation: IBasicNavigation): IMainFilesNavigation = remember(basicNavigation) {
    MainFilesNavigation(basicNavigation)
}