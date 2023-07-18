package com.samara.main_files.di

import com.samara.main_files.presentation.screens.files.MainFilesVm
import dagger.Binds
import dagger.Module
import di.vm.ViewModelAssistedFactory


@Module
interface VmModule {
    @Binds
    @MainFilesScope
    fun bindMainFilesVmFactory(factory: MainFilesVm.Factory): ViewModelAssistedFactory<MainFilesVm>
}