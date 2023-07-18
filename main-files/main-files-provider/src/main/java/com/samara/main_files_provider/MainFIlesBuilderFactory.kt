package com.samara.main_files_provider

import com.samara.main_files_api.di.MainFilesApiComponent
import com.samara.main_files_impl.di.DaggerMainFilesApiComponentImpl

fun MainFilesApiComponent.Companion.builder(): MainFilesApiComponent.Builder =
    DaggerMainFilesApiComponentImpl.builder()