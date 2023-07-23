package com.samara.simplefilemanager.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.samara.main_files.presentation.navigation.mainFilesGraph
import com.samara.main_files.presentation.navigation.rememberMainFilesNavigation
import di.base.ComponentsLifecycleHandler
import extensions.askFilesPermission
import presentation.navigation.rememberBasicNavigation

@Composable
fun MainActivityScreen(
    vm: MainActivityVm,
    navController: NavHostController = rememberNavController()
) {
    val state by vm.state.collectAsState()

    val basicNavigation = rememberBasicNavigation(navController)
    val mainFilesNavigation = rememberMainFilesNavigation(basicNavigation)

    CheckFileAccessPermissions(vm)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        when (state.permissionsIsGranted) {
            true -> {
                NavHost(
                    navController = navController,
                    startDestination = state.startDestination,
                    builder = {
                        mainFilesGraph(mainFilesNavigation) { ComponentsLifecycleHandler(it) }
                    }
                )
            }

            false -> {
                Text(text = "Permissions is not granted")
            }

            else -> {}
        }

    }

}

@Composable
fun CheckFileAccessPermissions(
    vm: MainActivityVm
) {
    val context = LocalContext.current

    val activity = LocalContext.current as? Activity
    val mainActivity = activity as? MainActivity

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        vm.dispatch(MainActivityAction.PermissionsGranted(isGranted))
    }

    val requestManagePermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    vm.dispatch(MainActivityAction.PermissionsGranted(true))
                } else {
                    vm.dispatch(MainActivityAction.PermissionsGranted(false))
                    Toast.makeText(
                        context,
                        "Allow permission for storage access!",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
        }
    )

    LaunchedEffect(true) {
        vm.effects.collect { e ->
            when(e) {
                is MainActivityEffect.CheckPermissions -> {

                    requestPermissions(
                        packageName = context.packageName,
                        requestUpperR = { intent ->
                            activity?.let {
                                requestManagePermission.launch(intent)
                            }
                        },
                        requestUnderR = {
                            mainActivity?.askFilesPermission(
                                askForPermission = {
                                    requestPermissionLauncher.launch(
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    )
                                },
                                onGranted = {
                                    vm.dispatch(MainActivityAction.PermissionsGranted(true))
                                }
                            )
                        },
                        isGranted = { vm.dispatch(MainActivityAction.PermissionsGranted(it)) }
                    )

                }
            }
        }
    }

    LaunchedEffect(true) {
        vm.dispatch(MainActivityAction.CheckPermissions)
    }
}

private fun requestPermissions(
    packageName: String,
    requestUpperR: (Intent) -> Unit = {},
    requestUnderR: () -> Unit = {},
    isGranted: (Boolean) -> Unit = {}
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (Environment.isExternalStorageManager()) {
            isGranted(true)
        } else {

            try {
                val intent =
                    Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(
                    String.format(
                        "package:%s",
                        packageName
                    )
                )

                requestUpperR(intent)

            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                requestUpperR(intent)
            }

        }
    } else {
        requestUnderR()
    }
}