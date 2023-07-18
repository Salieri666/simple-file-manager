package com.samara.simplefilemanager.di

import dagger.Module
import dagger.Provides
import di.base.IComponentsManager

@Module
class AppModule {
    @Provides
    @AppScope
    fun provideComponentsManager(): IComponentsManager = ComponentsManager()
}