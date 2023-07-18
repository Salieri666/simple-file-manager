package com.samara.simplefilemanager.di

import di.base.BaseComponentHolder

object AppComponentHolder : BaseComponentHolder<AppComponent, AppComponentDependencies>() {
    override fun build(dependencies: AppComponentDependencies): AppComponent {
        component =
            DaggerAppComponent.builder()
                .coreComponent(dependencies.coreComponent)
                .build()
        return component!!
    }
}