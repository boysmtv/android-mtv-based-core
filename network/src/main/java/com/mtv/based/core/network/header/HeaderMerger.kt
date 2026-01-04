/*
 * Project: Boys.mtv@gmail.com
 * File: HeaderMerger.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 20.53
 */

package com.mtv.based.core.network.header

import com.mtv.based.core.network.utils.BaseHeaderProvider
import com.mtv.based.core.network.utils.RequestOptions
import javax.inject.Inject

class HeaderMerger @Inject constructor(
    private val baseHeaderProvider: BaseHeaderProvider,
    private val additionalHeaderProvider: AdditionalHeaderProvider
) {

    fun build(options: RequestOptions): Map<String, String> {
        return buildMap {
            putAll(baseHeaderProvider.provide())
            putAll(additionalHeaderProvider.provide(options.requireAuth))
            putAll(options.headers)
        }
    }
}