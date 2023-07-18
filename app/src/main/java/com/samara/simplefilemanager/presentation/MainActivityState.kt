package com.samara.simplefilemanager.presentation

import com.samara.main_files.presentation.navigation.MainFilesDestination
import kotlinx.parcelize.Parcelize
import models.UiState
import presentation.base.State

@Parcelize
data class MainActivityState(
    val startDestination: String = MainFilesDestination.route,
    val uiState: UiState = UiState.Success,
    val permissionsIsGranted: Boolean? = null
) : State