package data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class PrefsStorage(
    private val context: Context
) : IPrefsStorage {

    companion object {
        const val PREFS_LABELS = "PREFS_LABELS"
        const val PREFS_ENCRYPTED_LABELS_KEY = "prefsEncryptedLabelsKey"
        const val PREFS_COMMON_LABELS_KEY = "prefsCommonLabelsKey"
    }

    private val labelsPrefs by lazy {
        context.getSharedPreferences(PREFS_LABELS, Context.MODE_PRIVATE)
    }

    private val commonPreferencesLabels: MutableSet<String>
        get() = labelsPrefs.getStringSet(PREFS_COMMON_LABELS_KEY, mutableSetOf()) ?: mutableSetOf()

    private val encryptedPreferencesLabels: MutableSet<String>
        get() = labelsPrefs.getStringSet(PREFS_ENCRYPTED_LABELS_KEY, mutableSetOf()) ?: mutableSetOf()

    private fun setCommonPreferencesLabels(labels: Set<String>) {
        labelsPrefs.edit().putStringSet(PREFS_COMMON_LABELS_KEY, labels).apply()
    }

    private fun setEncryptedPreferencesLabels(labels: Set<String>) {
        labelsPrefs.edit().putStringSet(PREFS_ENCRYPTED_LABELS_KEY, labels).apply()
    }

    override fun getPreferences(name: String, type: PrefsType): SharedPreferences {
        return when (type) {
            PrefsType.COMMON -> {
                commonPreferencesLabels.also {
                    if (it.contains(name).not()) {
                        it.add(name)
                        setCommonPreferencesLabels(it)
                    }
                }
                context.getSharedPreferences(name, Context.MODE_PRIVATE)
            }
            PrefsType.ENCRYPTED -> {
                encryptedPreferencesLabels.also {
                    if (it.contains(name).not()) {
                        it.add(name)
                        setEncryptedPreferencesLabels(it)
                    }
                }

                val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()
                EncryptedSharedPreferences.create(
                    context,
                    name,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            }
        }
    }

    override suspend fun clearEncrypted() {
        encryptedPreferencesLabels.forEach { clearPrefs(it, PrefsType.ENCRYPTED) }
    }

    override suspend fun clearAll() {
        commonPreferencesLabels.forEach { clearPrefs(it, PrefsType.COMMON) }
        clearEncrypted()
    }

    private fun clearPrefs(name: String, type: PrefsType) {
        getPreferences(name, type).edit().clear().commit()
    }

}