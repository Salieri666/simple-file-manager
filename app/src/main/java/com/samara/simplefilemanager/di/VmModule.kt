package com.samara.simplefilemanager.di

import androidx.lifecycle.ViewModel
import com.samara.simplefilemanager.presentation.MainActivityVm
import dagger.Binds
import dagger.Module
import di.vm.ViewModelAssistedFactory

@Module
interface VmModule {

    @Binds
    @AppScope
    fun bindMainActivityVm(viewModelFactory: MainActivityVm.Factory): ViewModelAssistedFactory<out ViewModel>

}