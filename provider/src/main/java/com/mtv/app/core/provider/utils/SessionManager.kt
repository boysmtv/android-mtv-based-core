package com.mtv.app.core.provider.utils

import com.mtv.app.core.provider.utils.Constants.Companion.Session.SESSION_IS_LOGGED_IN
import com.mtv.app.core.provider.utils.Constants.Companion.Session.SESSION_UID

class SessionManager(
    private val prefs: SecurePrefs
) {
    fun saveUid(uid: String) = prefs.putString(SESSION_UID, uid)

    fun getUid(): String? = prefs.getString(SESSION_UID)

    fun setLoggedIn(status: Boolean) = prefs.putBoolean(SESSION_IS_LOGGED_IN, status)

    fun isLoggedIn(): Boolean = prefs.getBoolean(SESSION_IS_LOGGED_IN)

    fun logout() = prefs.clear()
}
