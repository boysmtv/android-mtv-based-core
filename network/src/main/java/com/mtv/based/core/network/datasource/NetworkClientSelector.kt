package com.mtv.based.core.network.datasource

import com.mtv.based.core.network.config.NetworkConfigProvider
import com.mtv.based.core.network.di.KtorClient
import com.mtv.based.core.network.di.RetrofitClient
import javax.inject.Inject

class NetworkClientSelector @Inject constructor(
    private val config: NetworkConfigProvider,
    @KtorClient private val ktor: NetworkDataSource,
    @RetrofitClient private val retrofit: NetworkDataSource
) {

    fun get(): NetworkDataSource =
        if (config.provide().useKtor) ktor else retrofit
}