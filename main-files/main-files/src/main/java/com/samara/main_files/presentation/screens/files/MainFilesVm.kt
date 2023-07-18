package com.samara.main_files.presentation.screens.files

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.samara.main_files.presentation.mappers.toFileDomain
import com.samara.main_files.presentation.mappers.toFileUI
import com.samara.main_files_api.domain.useCase.IMainFilesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import di.vm.ViewModelAssistedFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import presentation.base.Action
import presentation.base.StoreVM
import presentation.base.change

class MainFilesVm @AssistedInject constructor(
    private val mainFilesUseCase: IMainFilesUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : StoreVM<MainFilesState>(savedStateHandle) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<MainFilesVm>

    override val initialState: MainFilesState
        get() = MainFilesState()

    init {
        mainFilesUseCase.setInitState(
            listFiles = actualState.files.map { it.toFileDomain() },
            depthNumber = actualState.depthNumber,
            currentPath = actualState.currentPath ?: ""
        )

        viewModelScope.launch {
            mainFilesUseCase.filesState.collect {
                dispatch(
                    MainFilesAction.StateChangedAction(
                        listFiles = it.listFiles,
                        depthNumber = it.depthNumber,
                        currentPath = it.currentPath
                    )
                )
            }
        }
    }

    override fun reduce(action: Action, state: MainFilesState): Flow<MainFilesState> {
        return when (action) {
            is MainFilesAction.InitRoot -> initRoot(action)
            is MainFilesAction.OpenDir -> openDir(action)
            is MainFilesAction.BackAction -> back(action)
            is MainFilesAction.StateChangedAction -> stateChanged(action)
            else -> super.reduce(action, state)
        }
    }

    private fun initRoot(action: MainFilesAction.InitRoot): Flow<MainFilesState> {
        return actualState.change {
            if (!actualState.isInitialized)
                mainFilesUseCase.initRoot()

            it.copy(isInitialized = true)
        }
    }

    private fun openDir(action: MainFilesAction.OpenDir): Flow<MainFilesState> {
        return action.ignoreState {
            mainFilesUseCase.openDir(action.path)
        }
    }

    private fun back(action: MainFilesAction.BackAction): Flow<MainFilesState> {
        return action.ignoreState {
            mainFilesUseCase.backAction()
        }
    }

    private fun stateChanged(action: MainFilesAction.StateChangedAction): Flow<MainFilesState> {
        return actualState.change {
            actualState.copy(
                depthNumber = action.depthNumber,
                files = action.listFiles.map { it.toFileUI() },
                currentPath = action.currentPath
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        mainFilesUseCase.onCleared()
    }
}