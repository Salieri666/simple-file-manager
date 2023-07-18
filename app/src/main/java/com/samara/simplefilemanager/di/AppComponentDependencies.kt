package com.samara.simplefilemanager.di

import di.base.ComponentDependencies
import di.components.CoreComponent

data class AppComponentDependencies(
    val coreComponent: CoreComponent,
) : ComponentDependencies
