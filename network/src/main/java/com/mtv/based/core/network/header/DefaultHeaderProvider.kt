/*
 * Project: Boys.mtv@gmail.com
 * File: DefaultHeaderProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 19.15
 */

package com.mtv.based.core.network.header

import com.mtv.based.core.network.utils.TokenProvider
import javax.inject.Inject

class DefaultHeaderProvider @Inject constructor(
    private val tokenProvider: TokenProvider
) : HeaderProvider {

    override fun getHeaders(requireAuth: Boolean): Map<String, String> {
        val headers = mutableMapOf(
            "Content-Type" to "application/json"
        )

        if (requireAuth) {
            tokenProvider.get()?.let {
                headers["Authorization"] = "Bearer $it"
            }
        }

        return headers
    }
}
