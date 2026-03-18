package com.mtv.based.core.provider.utils

import com.mtv.based.core.provider.utils.Constants.Companion.Session.SESSION_IS_LOGGED_IN
import com.mtv.based.core.provider.utils.Constants.Companion.Session.SESSION_UID
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SessionManager(
    private val prefs: SecurePrefs
) {
    fun saveUid(uid: String) = prefs.putString(SESSION_UID, uid)

    fun getUid(): String? = prefs.getString(SESSION_UID)

    fun setLoggedIn(status: Boolean) = prefs.putBoolean(SESSION_IS_LOGGED_IN, status)

    fun isLoggedIn(): Boolean = prefs.getBoolean(SESSION_IS_LOGGED_IN)

    fun logout() = prefs.clear()

    private val _logoutEvent = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1
    )

    val logoutEvent: SharedFlow<Unit> = _logoutEvent.asSharedFlow()

    suspend fun forceLogout() {
        prefs.clear()
        _logoutEvent.emit(Unit)
    }
}
