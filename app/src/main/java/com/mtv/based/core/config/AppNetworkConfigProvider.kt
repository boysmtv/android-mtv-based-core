package com.mtv.based.core.config

import com.mtv.based.core.BuildConfig
import com.mtv.based.core.network.config.AppNetworkConfig
import com.mtv.based.core.network.config.NetworkConfig
import com.mtv.based.core.network.config.NetworkConfigProvider

class AppNetworkConfigProvider : NetworkConfigProvider {

    override fun provide(): NetworkConfig =
        AppNetworkConfig(
            baseUrl = BuildConfig.BASE_URL,
            useKtor = BuildConfig.USE_KTOR,
            isDebug = BuildConfig.DEBUG
        )

}
