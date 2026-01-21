package com.mtv.app.core.provider.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit
import com.google.gson.Gson

class SecurePrefs(context: Context) {

    private val gson = Gson()

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun putString(key: String, value: String) {
        prefs.edit { putString(key, value) }
    }

    fun getString(key: String, default: String? = null): String? =
        prefs.getString(key, default)

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean =
        prefs.getBoolean(key, default)

    fun clear() {
        prefs.edit { clear() }
    }

    /* ============================================================
     * OBJECT HANDLER
     * ============================================================ */

    fun <T> putObject(key: String, obj: T) {
        val json = gson.toJson(obj)
        prefs.edit {
            putString(key, json)
        }
    }

    fun <T> getObject(key: String, clazz: Class<T>): T? {
        val json = prefs.getString(key, null) ?: return null
        return try {
            gson.fromJson(json, clazz)
        } catch (e: Exception) {
            null
        }
    }

}
