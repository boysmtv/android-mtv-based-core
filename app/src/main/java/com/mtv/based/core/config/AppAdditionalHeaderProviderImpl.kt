/*
 * Project: Boys.mtv@gmail.com
 * File: AppAdditionalHeaderProviderImpl.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 21.19
 */

package com.mtv.based.core.config

import com.mtv.based.core.network.header.AppAdditionalHeaderProvider
import javax.inject.Inject

class AppAdditionalHeaderProviderImpl @Inject constructor() :
    AppAdditionalHeaderProvider {

    override fun getAdditionalHeaders(): Map<String, String> =
        mapOf(
            "X-App-Version" to "1.0.0",
            "X-Platform" to "android"
        )
}
