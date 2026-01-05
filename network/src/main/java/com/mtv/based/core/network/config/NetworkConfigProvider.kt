/*
 * Project: Boys.mtv@gmail.com
 * File: NetworkConfigProvider.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 14.26
 */

package com.mtv.based.core.network.config

interface NetworkConfigProvider {
    fun provide(): NetworkConfig
}