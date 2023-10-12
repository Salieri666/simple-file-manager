package com.samara.main_files.presentation.screens.files

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.samara.main_files.R
import com.samara.main_files.presentation.mappers.toFileDomain
import com.samara.main_files.presentation.mappers.toFileUI
import com.samara.main_files_api.domain.useCase.IMainFilesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import data.resourceProvider.IResourceProvider
import di.vm.ViewModelAssistedFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import models.UiState
import models.UiState.Companion.isLoading
import presentation.base.Action
import presentation.base.StoreVM
import presentation.base.change

class MainFilesVm @AssistedInject constructor(
    private val mainFilesUseCase: IMainFilesUseCase,
    private val resourceProvider: IResourceProvider,
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
            currentPath = actualState.currentPath ?: "",
            loading = actualState.uiState.isLoading
        )

        viewModelScope.launch {
            mainFilesUseCase.filesState.collect {
                dispatch(
                    MainFilesAction.StateChangedAction(
                        listFiles = it.listFiles,
                        depthNumber = it.depthNumber,
                        currentPath = it.currentPath,
                        loading = it.loading
                    )
                )
            }
        }
    }

    override fun reduce(action: Action, state: MainFilesState): Flow<MainFilesState> {
        return when (action) {
            is MainFilesAction.InitRoot -> initRoot(action)
            is MainFilesAction.BackAction -> back(action)
            is MainFilesAction.StateChangedAction -> stateChanged(action)
            is MainFilesAction.ToEditMode -> toEditMode(action)
            is MainFilesAction.ClickOnElement -> clickOnElement(action)
            is MainFilesAction.ClickOnBottomActions -> clickOnBottomActions(action)
            is MainFilesAction.CloseDialog -> closeDialog(action)
            is MainFilesAction.RenameItemAction -> renameItem(action)
            else -> super.reduce(action, state)
        }
    }

    private fun initRoot(action: MainFilesAction.InitRoot): Flow<MainFilesState> {
        return actualState.change {
            if (!actualState.isInitialized)
                mainFilesUseCase.initRoot()

            it.copy(
                isInitialized = true
            )
        }
    }

    private fun openDir(action: MainFilesAction.ClickOnElement): Flow<MainFilesState> {
        return action.ignoreState {
            mainFilesUseCase.openDir(action.file.absolutePath)
        }
    }

    private fun back(action: MainFilesAction.BackAction): Flow<MainFilesState> {
        return actualState.change {
            if (it.editMode) {
                return@change it.copy(
                    editMode = false,
                    files = it.files.map { file -> file.copy(isChecked = false) }
                )
            }

            mainFilesUseCase.backAction()
            return@change it
        }
    }

    private fun stateChanged(action: MainFilesAction.StateChangedAction): Flow<MainFilesState> {
        return actualState.change {
            actualState.copy(
                depthNumber = action.depthNumber,
                files = action.listFiles.map { it.toFileUI() },
                currentPath = action.currentPath,
                uiState = if (!action.loading) UiState.Success else UiState.Loading
            )
        }
    }

    private fun openFile(action: MainFilesAction.ClickOnElement): Flow<MainFilesState> {
        return action.ignoreState {
            action.file.absolutePath.let {
                effect(
                    MainFilesEffect.OpenFile(
                        mainFilesUseCase.convertPathToUri(action.file.absolutePath), action.file.ext
                    )
                )
            }
        }
    }

    private fun toEditMode(action: MainFilesAction.ToEditMode): Flow<MainFilesState> {
        if (actualState.editMode) return action.ignoreState()

        return actualState.change {
            val newFiles = it.files.map { file ->
                if (file.absolutePath == action.file.absolutePath)
                    file.copy(isChecked = !file.isChecked)
                else
                    file
            }

            val selectedItemsSize = newFiles.filter { el -> el.isChecked }.size
            val selectedItemsText = resourceProvider.getQuantityString(
                R.plurals.selectedItems,
                selectedItemsSize,
                selectedItemsSize
            )

            val newBottomActions = it.bottomActions.toMutableList().map { el ->
                if (el.type == BottomFileActionType.RENAME)
                    el.copy(isActive = selectedItemsSize == 1)
                else {
                    el.copy(isActive = selectedItemsSize > 0)
                }
            }

            it.copy(
                editMode = true,
                files = newFiles,
                selectedItemsText = selectedItemsText,
                bottomActions = newBottomActions
            )
        }
    }

    private fun clickOnElement(action: MainFilesAction.ClickOnElement): Flow<MainFilesState> {
        return when {
            actualState.editMode -> checkElement(action)
            action.file.isDir -> openDir(action)
            else -> openFile(action)
        }
    }

    private fun checkElement(action: MainFilesAction.ClickOnElement): Flow<MainFilesState> {
        return actualState.change {
            val newFiles = it.files.map { file ->
                if (file.absolutePath == action.file.absolutePath)
                    file.copy(isChecked = !file.isChecked)
                else
                    file
            }

            val selectedItemsSize = newFiles.filter { el -> el.isChecked }.size
            val selectedItemsText = resourceProvider.getQuantityString(
                R.plurals.selectedItems,
                selectedItemsSize,
                selectedItemsSize
            )

            val newBottomActions = it.bottomActions.toMutableList().map { el ->
                    if (el.type == BottomFileActionType.RENAME)
                        el.copy(isActive = selectedItemsSize == 1)
                    else {
                        el.copy(isActive = selectedItemsSize > 0)
                    }
            }

            it.copy(
                editMode = true,
                files = newFiles,
                selectedItemsText = selectedItemsText,
                bottomActions = newBottomActions
            )
        }
    }

    private fun clickOnBottomActions(action: MainFilesAction.ClickOnBottomActions): Flow<MainFilesState> {
        val currentCheckedElements = actualState.files.filter { el -> el.isChecked }

        return actualState.change {

            when (action.action) {
                BottomFileActionType.MOVE -> {

                }

                BottomFileActionType.DELETE -> {
                    mainFilesUseCase.delete(it.files.filter { el -> el.isChecked }
                        .map { el -> el.toFileDomain() })
                    return@change it.copy(
                        editMode = false,
                        files = actualState.files.filter { el -> !el.isChecked })
                }

                BottomFileActionType.DETAIL -> {
                    return@change it.copy(
                        openDialogDetails = true,
                        countsDialogDetails = currentCheckedElements.size,
                        lastChanged = if (currentCheckedElements.size == 1) currentCheckedElements[0].changedDate else "",
                        size = mainFilesUseCase.humanReadableByteCountBin(currentCheckedElements.sumOf { fileUi -> fileUi.sizeBytes })
                    )
                }

                BottomFileActionType.RENAME -> {
                    return@change if (currentCheckedElements.size == 1) {
                        it.copy(
                            openRenameDialog = true,
                            textForRename = currentCheckedElements[0].title
                        )
                    } else it
                }
            }

            it
        }

    }

    private fun closeDialog(action: MainFilesAction.CloseDialog): Flow<MainFilesState> =
        actualState.change {
            it.copy(openDialogDetails = false, openRenameDialog = false,showInvalidTitle = false,
                showDuplicateMsg = false)
        }

    private fun renameItem(action: MainFilesAction.RenameItemAction): Flow<MainFilesState> {
        if (!mainFilesUseCase.checkTitleFile(action.title)) {
            return actualState.change {
                it.copy(
                    showInvalidTitle = true,
                    showDuplicateMsg = false
                )
            }
        }

        //check on duplicate

        if (actualState.files.any { it.title == action.title }) {
            return actualState.change {
                it.copy(
                    showInvalidTitle = false,
                    showDuplicateMsg = true
                )
            }
        }

        return actualState.change { state ->
            mainFilesUseCase.renameFile(action.title, actualState.files.first {
                it.isChecked
            }.absolutePath)

            state.copy(
                openRenameDialog = false,
                editMode = false,
                showInvalidTitle = false,
                showDuplicateMsg = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        mainFilesUseCase.onCleared()
    }
}