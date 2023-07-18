package com.samara.main_files.di

import com.samara.main_files_api.di.MainFilesApiComponent
import com.samara.main_files_provider.builder
import di.base.BaseComponentHolder
import di.components.CoreComponentHolder

object MainFilesComponentHolder :
    BaseComponentHolder<MainFilesComponent, MainFilesComponentDependencies>() {
    override fun build(dependencies: MainFilesComponentDependencies): MainFilesComponent {
        component =
            DaggerMainFilesComponent.builder()
                .coreComponent(dependencies.coreComponent)
                .mainFilesApiComponent(
                    MainFilesApiComponent
                        .builder()
                        .coreComponent(
                            dependencies.coreComponent
                        )
                        .build()
                )
                .build()

        return component!!
    }
}

fun MainFilesComponentHolder.initComponents() {
    init(
        MainFilesComponentDependencies(
            coreComponent = CoreComponentHolder.get()
        )
    )
}