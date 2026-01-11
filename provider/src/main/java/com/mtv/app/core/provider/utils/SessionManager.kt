package com.mtv.app.core.provider.utils

class SessionManager(
    private val prefs: SecurePrefs
) {
    fun saveUid(uid: String) {
        prefs.putString("uid", uid)
    }

    fun getUid(): String? = prefs.getString("uid")

    fun setLoggedIn(status: Boolean) = prefs.putBoolean("isLoggedIn", status)

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn")

    fun logout() = prefs.clear()
}
