package com.mtv.based.core.network.utils

import com.mtv.based.core.network.di.KtorClient
import com.mtv.based.core.network.di.RetrofitClient
import javax.inject.Inject

class NetworkClientSelector @Inject constructor(
    @KtorClient private val ktor: NetworkClientInterface,
    @RetrofitClient private val retrofit: NetworkClientInterface
) {

    fun get(): NetworkClientInterface =
        if (NetworkConfig.USE_KTOR) ktor else retrofit
}
