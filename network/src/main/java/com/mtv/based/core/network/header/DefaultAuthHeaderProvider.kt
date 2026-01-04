/*
 * Project: Boys.mtv@gmail.com
 * File: DefaultBaseHeaderProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 20.51
 */

package com.mtv.based.core.network.header

import com.mtv.based.core.network.utils.TokenProvider
import javax.inject.Inject

class DefaultAuthHeaderProvider @Inject constructor(
    private val tokenProvider: TokenProvider
) : AdditionalHeaderProvider {

    override fun provide(requireAuth: Boolean): Map<String, String> {
        if (!requireAuth) return emptyMap()

        return tokenProvider.get()?.let {
            mapOf("Authorization" to "Bearer $it")
        } ?: emptyMap()
    }

}
