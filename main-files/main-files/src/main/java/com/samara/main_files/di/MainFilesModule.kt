package com.samara.main_files.di

import dagger.Module

@Module(includes = [MainFilesModule.Bindings::class])
class MainFilesModule {

    @Module
    interface Bindings
}