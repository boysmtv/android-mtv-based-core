package com.mtv.based.core.network.config

class AppNetworkConfig(
    override val baseUrl: String,
    override val useKtor: Boolean,
    override val isDebug: Boolean
) : NetworkConfig