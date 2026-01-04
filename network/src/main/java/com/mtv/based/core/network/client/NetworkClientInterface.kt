package com.mtv.based.core.network.client

import com.mtv.based.core.network.model.NetworkResponse

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

    suspend fun put(
        endpoint: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): NetworkResponse

    suspend fun delete(
        endpoint: String,
        headers: Map<String, String> = emptyMap()
    ): NetworkResponse

}
