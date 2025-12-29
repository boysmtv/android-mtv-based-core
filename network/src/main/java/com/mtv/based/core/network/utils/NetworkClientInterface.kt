package com.mtv.based.core.network.utils

interface NetworkClientInterface {

    suspend fun get(
        endpoint: String,
        query: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): NetworkResponse

    suspend fun post(
        endpoint: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): NetworkResponse
}
