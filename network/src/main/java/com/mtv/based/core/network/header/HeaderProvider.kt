/*
 * Project: Boys.mtv@gmail.com
 * File: HeaderProvider.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 19.15
 */

package com.mtv.based.core.network.header

interface HeaderProvider {
    fun getHeaders(requireAuth: Boolean = true): Map<String, String>
}
