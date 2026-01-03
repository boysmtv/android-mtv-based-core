package com.mtv.based.core.config

import com.mtv.based.core.BuildConfig
import com.mtv.based.core.network.utils.NetworkConfig
import com.mtv.based.core.network.utils.NetworkConfigProvider

class AppNetworkConfigProvider : NetworkConfigProvider {
    override fun provide(): NetworkConfig =
        AppNetworkConfig(
            baseUrl = BuildConfig.BASE_URL,
            useKtor = BuildConfig.USE_KTOR
        )
}
