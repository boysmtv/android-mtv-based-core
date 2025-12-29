package com.mtv.based.core.network.utils

import javax.inject.Inject

class NetworkRepository @Inject constructor(
    selector: NetworkClientSelector
) {

    private val client: NetworkClientInterface = selector.get()

    private val defaultHeader = mapOf(
        "Authorization" to "Bearer default_token",
        "Content-Type" to "application/json"
    )

    suspend fun get(
        endpoint: String,
        options: RequestOptions = RequestOptions()
    ): NetworkResponse {
        val headers = options.headers.ifEmpty { defaultHeader }
        return client.get(endpoint, query = options.query, headers = headers)
    }

    suspend fun post(
        endpoint: String,
        body: Any,
        options: RequestOptions = RequestOptions()
    ): NetworkResponse {
        val headers = options.headers.ifEmpty { defaultHeader }
        return client.post(endpoint, body, headers = headers)
    }
}
