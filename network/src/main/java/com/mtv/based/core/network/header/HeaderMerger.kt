/*
 * Project: Boys.mtv@gmail.com
 * File: HeaderMerger.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 20.53
 */

package com.mtv.based.core.network.header

import java.util.Optional
import javax.inject.Inject

class HeaderMerger @Inject constructor(
    private val base: BaseHeaderProvider,
    private val additional: Optional<AdditionalHeaderProvider>
) {

    fun build(): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        headers.putAll(base.provide())

        additional.ifPresent {
            headers.putAll(it.provide())
        }

        return headers
    }
}
