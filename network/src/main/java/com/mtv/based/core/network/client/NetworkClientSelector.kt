package com.mtv.based.core.network.client

import com.mtv.based.core.network.di.KtorClient
import com.mtv.based.core.network.di.RetrofitClient
import com.mtv.based.core.network.config.NetworkConfigProvider
import javax.inject.Inject

class NetworkClientSelector @Inject constructor(
    private val config: NetworkConfigProvider,
    @KtorClient private val ktor: NetworkClientInterface,
    @RetrofitClient private val retrofit: NetworkClientInterface
) {

    fun get(): NetworkClientInterface =
        if (config.provide().useKtor) ktor else retrofit
}
