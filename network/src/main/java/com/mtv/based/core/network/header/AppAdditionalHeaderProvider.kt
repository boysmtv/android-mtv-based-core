package com.mtv.based.core.network.header

import com.mtv.based.core.network.utils.TokenProvider
import javax.inject.Inject

class AppAdditionalHeaderProvider @Inject constructor(
    private val tokenProvider: TokenProvider
) : AdditionalHeaderProvider {

    override fun provide(requireAuth: Boolean): Map<String, String> {
        if (!requireAuth) return emptyMap()

        return tokenProvider.get()?.let {
            mapOf("Authorization" to "Bearer $it")
        } ?: emptyMap()
    }

}