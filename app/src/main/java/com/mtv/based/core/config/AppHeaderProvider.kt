/*
 * Project: Boys.mtv@gmail.com
 * File: AppHeaderProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 21.27
 */

package com.mtv.based.core.config

import com.mtv.based.core.network.header.HeaderProvider
import javax.inject.Inject

class AppHeaderProvider @Inject constructor() : HeaderProvider {

    override fun getHeaders(requireAuth: Boolean): Map<String, String> = mapOf(
        "X-App-Version" to "1.0.0",
        "X-Platform" to "android"
    )
}
