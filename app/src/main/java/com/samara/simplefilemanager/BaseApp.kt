package com.samara.simplefilemanager

import android.app.Application
import com.samara.simplefilemanager.di.AppComponentDependencies
import com.samara.simplefilemanager.di.AppComponentHolder
import di.components.AppComponent
import di.components.CoreComponent
import di.components.CoreComponentDependencies
import di.components.CoreComponentHolder
import di.components.DaggerAppComponent

open class BaseApp : Application() {
    private var appComponent: AppComponent? = null
    private var coreComponent: CoreComponent? = null

    protected fun appComponent(): AppComponent = appComponent ?: run {
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent!!
    }

    protected fun coreComponent(): CoreComponent = coreComponent ?: run {
        coreComponent = CoreComponentHolder.init(
            CoreComponentDependencies(appComponent = appComponent())
        )
        coreComponent!!
    }

    override fun onCreate() {
        super.onCreate()
        AppComponentHolder.init(
            AppComponentDependencies(
                coreComponent()
            )
        )
    }
}