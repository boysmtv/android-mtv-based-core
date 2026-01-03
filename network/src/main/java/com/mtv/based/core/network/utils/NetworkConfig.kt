/*
 * Project: Boys.mtv@gmail.com
 * File: NetworkConfig.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 14.26
 */

package com.mtv.based.core.network.utils

interface NetworkConfig {
    val baseUrl: String
    val useKtor: Boolean
    val isDebug: Boolean
}