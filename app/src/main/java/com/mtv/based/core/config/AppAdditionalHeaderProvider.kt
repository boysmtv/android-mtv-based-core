/*
 * Project: Boys.mtv@gmail.com
 * File: AppAdditionalHeaderProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 21.25
 */

package com.mtv.based.core.config

import com.mtv.based.core.network.header.AdditionalHeaderProvider

class AppAdditionalHeaderProvider : AdditionalHeaderProvider {

    override fun provide(): Map<String, String> =
        mapOf(
            "X-App-Version" to "1.0.0",
            "X-Platform" to "android"
        )

}
