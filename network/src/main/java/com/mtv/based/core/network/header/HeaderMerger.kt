/*
 * Project: Boys.mtv@gmail.com
 * File: HeaderMerger.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 20.53
 */

package com.mtv.based.core.network.header

import javax.inject.Inject

class HeaderMerger @Inject constructor(
    private val baseHeaderProvider: BaseHeaderProvider,
    private val additionalHeaderProvider: AdditionalHeaderProvider
) {

    fun build(): Map<String, String> =
        buildMap {
            putAll(baseHeaderProvider.provide())
            putAll(additionalHeaderProvider.provide())
        }
}