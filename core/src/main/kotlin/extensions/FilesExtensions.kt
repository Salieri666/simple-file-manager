package extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log


fun Context.openFile(filePath: Uri, type: String) {

    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        setDataAndType(filePath, type)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    try {
        startActivity(intent)
    } catch (e: Exception) {
        Log.e("OPEN_FILE", e.message ?: "no message")
    }
}