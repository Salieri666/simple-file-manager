package data.pref

import android.content.SharedPreferences

interface IPrefsStorage {

    fun getPreferences(name: String, type: PrefsType): SharedPreferences

    suspend fun clearEncrypted()

    suspend fun clearAll()
}

enum class PrefsType {
    ENCRYPTED, COMMON
}