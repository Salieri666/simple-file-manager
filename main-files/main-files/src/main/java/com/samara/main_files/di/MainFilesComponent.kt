package com.samara.main_files.di

import com.samara.main_files.presentation.screens.files.MainFilesVm
import com.samara.main_files_api.di.MainFilesApiComponent
import dagger.Component
import di.components.CoreComponent

@Component(
    modules = [MainFilesModule::class, VmModule::class],
    dependencies = [CoreComponent::class, MainFilesApiComponent::class]
)
@MainFilesScope
interface MainFilesComponent {

    fun mainFilesVmFactory(): MainFilesVm.Factory
}