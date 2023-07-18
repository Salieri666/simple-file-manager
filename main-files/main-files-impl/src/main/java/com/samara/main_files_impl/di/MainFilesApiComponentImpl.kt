package com.samara.main_files_impl.di

import com.samara.main_files_api.di.MainFilesApiComponent
import dagger.Component
import di.components.CoreComponent

@Component(
    modules = [MainFilesModuleImpl::class],
    dependencies = [CoreComponent::class]
)
@MainFilesImplScope
interface MainFilesApiComponentImpl : MainFilesApiComponent {

    @Component.Builder
    interface Builder : MainFilesApiComponent.Builder
}