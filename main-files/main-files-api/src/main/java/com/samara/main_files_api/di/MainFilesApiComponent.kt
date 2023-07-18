package com.samara.main_files_api.di

import com.samara.main_files_api.data.IFilesRepo
import com.samara.main_files_api.domain.useCase.IMainFilesUseCase
import di.components.CoreComponent

interface MainFilesApiComponent {

    interface Builder {
        fun coreComponent(component: CoreComponent): Builder

        fun build(): MainFilesApiComponent
    }

    fun getMainFilesUseCase(): IMainFilesUseCase

    fun getFilesRepo(): IFilesRepo

    companion object
}