package com.mtv.based.core.network.client

import com.mtv.based.core.network.config.NetworkConfigProvider
import com.mtv.based.core.network.datasource.NetworkDataSource
import com.mtv.based.core.network.datasource.RetrofitDataSource
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.model.RawNetworkResponse
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val apiService: RetrofitDataSource,
    config: NetworkConfigProvider,
) : NetworkDataSource {

    private val baseUrl = config.provide().baseUrl

    override suspend fun get(
        endpoint: String,
        query: Map<String, String>,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = apiService.get("$baseUrl$endpoint", query, headers)

        val rawBody = if (response.isSuccessful) {
            response.body().orEmpty()
        } else {
            response.errorBody()?.string().orEmpty()
        }

        return RawNetworkResponse(
            httpCode = response.code(),
            body = rawBody
        )
    }

    override suspend fun post(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = apiService.post("$baseUrl$endpoint", body, headers)

        val rawBody = if (response.isSuccessful) {
            response.body().orEmpty()
        } else {
            response.errorBody()?.string().orEmpty()
        }

        return RawNetworkResponse(
            httpCode = response.code(),
            body = rawBody
        )
    }

    override suspend fun put(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = apiService.put("$baseUrl$endpoint", body, headers)

        val rawBody = if (response.isSuccessful) {
            response.body().orEmpty()
        } else {
            response.errorBody()?.string().orEmpty()
        }

        return RawNetworkResponse(
            httpCode = response.code(),
            body = rawBody
        )
    }

    override suspend fun delete(
        endpoint: String,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = apiService.delete("$baseUrl$endpoint", headers)

        val rawBody = if (response.isSuccessful) {
            response.body().orEmpty()
        } else {
            response.errorBody()?.string().orEmpty()
        }

        return RawNetworkResponse(
            httpCode = response.code(),
            body = rawBody
        )
    }
}
