/*
 * Project: Boys.mtv@gmail.com
 * File: EmptyAdditionalHeaderProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 23.04
 */

package com.mtv.based.core.network.header

class EmptyAdditionalHeaderProvider : AdditionalHeaderProvider {

    override fun provide(): Map<String, String> =
        emptyMap()

}
