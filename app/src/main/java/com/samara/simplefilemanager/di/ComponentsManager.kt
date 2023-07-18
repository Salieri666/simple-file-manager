package com.samara.simplefilemanager.di

import com.samara.main_files.di.MainFilesComponentHolder
import com.samara.main_files.di.initComponents
import com.samara.main_files.presentation.navigation.MainFilesDestination
import di.base.IComponentsManager
import presentation.navigation.Destination

/**
 * Implements [IComponentsManager]
 */
class ComponentsManager : IComponentsManager {
    override fun attachComponentTo(destination: Destination) {
        when (destination) {
            MainFilesDestination -> MainFilesComponentHolder.initComponents()
        }
    }

    override fun detachComponentFrom(destination: Destination) {
        when (destination) {
            MainFilesDestination -> MainFilesComponentHolder.clear()
        }
    }
}