package com.mtv.based.core.config

import com.mtv.based.core.network.utils.NetworkConfig

class AppNetworkConfig(
    override val baseUrl: String,
    override val useKtor: Boolean,
    override val isDebug: Boolean
) : NetworkConfig