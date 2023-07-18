package com.samara.simplefilemanager.di

import com.samara.simplefilemanager.presentation.MainActivity
import com.samara.simplefilemanager.presentation.MainActivityVm
import dagger.Component
import di.components.CoreComponent

@AppScope
@Component(
    modules = [VmModule::class, AppModule::class],
    dependencies = [CoreComponent::class]
)
interface AppComponent {
    fun mainActivityVmFactory(): MainActivityVm.Factory
    fun inject(activity: MainActivity)
}