package com.samara.simplefilemanager.presentation

import presentation.base.Action

sealed interface MainActivityAction : Action {
    data class PermissionsGranted(
        val isGranted: Boolean
    ) : MainActivityAction
    object CheckPermissions : MainActivityAction
}

sealed interface MainActivityEffect {
    object CheckPermissions : MainActivityEffect
}