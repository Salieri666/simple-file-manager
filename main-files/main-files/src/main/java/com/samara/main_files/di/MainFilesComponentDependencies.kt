package com.samara.main_files.di

import di.base.ComponentDependencies
import di.components.CoreComponent


data class MainFilesComponentDependencies(
    val coreComponent: CoreComponent
) : ComponentDependencies