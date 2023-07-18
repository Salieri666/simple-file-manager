package com.samara.simplefilemanager.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.samara.simplefilemanager.di.AppComponentHolder
import di.base.IComponentsManager
import di.base.LocalComponentsManager
import extensions.getVm
import theme.SimpleFileManagerTheme
import javax.inject.Inject

open class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @Inject
    lateinit var componentsManager: IComponentsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AppComponentHolder.component?.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            CompositionLocalProvider(LocalComponentsManager provides componentsManager) {
                SimpleFileManagerTheme {
                    val vm = LocalSavedStateRegistryOwner.current.getVm(
                        AppComponentHolder.get().mainActivityVmFactory()
                    )

                    Box(
                        modifier = Modifier
                            .systemBarsPadding()
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                    ) {
                        MainActivityScreen(
                            vm = vm,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}