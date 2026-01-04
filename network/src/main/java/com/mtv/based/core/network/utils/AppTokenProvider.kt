/*
 * Project: Boys.mtv@gmail.com
 * File: AppTokenProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 19.15
 */

package com.mtv.based.core.network.utils

import com.google.android.gms.cast.framework.SessionManager
import javax.inject.Inject

class AppTokenProvider @Inject constructor(
    private val sessionManager: SessionManager
) : TokenProvider {

    override fun get(): String? = sessionManager.token
}

