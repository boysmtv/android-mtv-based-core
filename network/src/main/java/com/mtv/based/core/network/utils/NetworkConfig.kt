package com.mtv.based.core.network.utils

interface NetworkConfig {
    val baseUrl: String
    val useKtor: Boolean
}

interface NetworkConfigProvider {
    fun provide(): NetworkConfig
}