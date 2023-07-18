package com.samara.simplefilemanager.presentation

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import di.vm.ViewModelAssistedFactory
import kotlinx.coroutines.flow.Flow
import presentation.base.Action
import presentation.base.StoreVM
import presentation.base.change

class MainActivityVm @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle
) : StoreVM<MainActivityState>(savedStateHandle) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<MainActivityVm>

    override val initialState: MainActivityState
        get() = MainActivityState()


    override fun reduce(action: Action, state: MainActivityState): Flow<MainActivityState> {
        return when(action) {
            is MainActivityAction.CheckPermissions -> handleCheckPermissions(action)
            is MainActivityAction.PermissionsGranted -> handlePermissionsGranted(action)
            else -> super.reduce(action, state)
        }
    }

    private fun handlePermissionsGranted(action: MainActivityAction.PermissionsGranted): Flow<MainActivityState> {
        return actualState.change {
            it.copy(
                permissionsIsGranted = action.isGranted
            )
        }
    }

    private fun handleCheckPermissions(action: MainActivityAction.CheckPermissions): Flow<MainActivityState> {
        return action.ignoreState {
            effect(MainActivityEffect.CheckPermissions)
        }
    }
}