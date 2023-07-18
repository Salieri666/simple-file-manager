package extensions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Activity.askFilesPermission(
    askForPermission: () -> Unit,
    onGranted: (() -> Unit)? = null,
    showPermissionRationale: (() -> Unit)? = null
) {
    val r = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
    val r1 = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    when {
        r && r1 -> onGranted?.invoke()
        shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> showPermissionRationale?.invoke()
        else -> askForPermission()
    }
}