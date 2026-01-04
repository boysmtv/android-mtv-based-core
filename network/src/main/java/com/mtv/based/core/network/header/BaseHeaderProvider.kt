/*
 * Project: Boys.mtv@gmail.com
 * File: BaseHeaderProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 22.37
 */

package com.mtv.based.core.network.header

interface BaseHeaderProvider {
    fun provide(): Map<String, String>
}
