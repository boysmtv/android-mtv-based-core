/*
 * Project: Boys.mtv@gmail.com
 * File: BaseHeaderProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 20.51
 */

package com.mtv.based.core.network.utils

interface BaseHeaderProvider {
    fun provide(): Map<String, String>
}
