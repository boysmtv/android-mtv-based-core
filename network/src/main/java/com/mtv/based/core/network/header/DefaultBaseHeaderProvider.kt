/*
 * Project: Boys.mtv@gmail.com
 * File: DefaultBaseHeaderProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 22.37
 */

package com.mtv.based.core.network.header

class DefaultBaseHeaderProvider : BaseHeaderProvider {

    override fun provide(): Map<String, String> =
        mapOf(
            "Content-Type" to "application/json"
        )
}
