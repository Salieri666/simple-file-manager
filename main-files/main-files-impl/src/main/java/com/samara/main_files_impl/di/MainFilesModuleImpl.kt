package com.samara.main_files_impl.di

import com.samara.main_files_api.data.IFilesRepo
import com.samara.main_files_api.domain.useCase.IMainFilesUseCase
import com.samara.main_files_impl.data.FilesRepo
import com.samara.main_files_impl.domain.MainFilesUseCase
import dagger.Binds
import dagger.Module

@Module(includes = [MainFilesModuleImpl.Bindings::class])
class MainFilesModuleImpl {

    @Module
    interface Bindings {

        @Binds
        @MainFilesImplScope
        fun bindMainFilesUseCase(mainFilesUseCase: MainFilesUseCase): IMainFilesUseCase

        @Binds
        @MainFilesImplScope
        fun bindFilesRepo(filesRepo: FilesRepo): IFilesRepo
    }
}