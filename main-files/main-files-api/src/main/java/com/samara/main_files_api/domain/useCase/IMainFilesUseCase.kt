package com.samara.main_files_api.domain.useCase

import android.net.Uri
import com.samara.main_files_api.domain.models.FileDomain
import kotlinx.coroutines.flow.StateFlow

interface IMainFilesUseCase {

    interface State {
        val listFiles: List<FileDomain>
        val depthNumber: Long
        val currentPath: String
    }

    val filesState: StateFlow<State>

    fun openDir(path: String?)

    fun initRoot()

    fun backAction()

    fun onCleared()

    fun setInitState(
        listFiles: List<FileDomain>,
        depthNumber: Long,
        currentPath: String
    )

    fun convertPathToUri(path: String): Uri
}