package com.mtv.based.core.network.datasource

import com.mtv.based.core.network.model.RawNetworkResponse

interface NetworkDataSource {

    suspend fun get(
        endpoint: String,
        query: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): RawNetworkResponse

    suspend fun post(
        endpoint: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): RawNetworkResponse

    suspend fun put(
        endpoint: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): RawNetworkResponse

    suspend fun delete(
        endpoint: String,
        headers: Map<String, String> = emptyMap()
    ): RawNetworkResponse
}
