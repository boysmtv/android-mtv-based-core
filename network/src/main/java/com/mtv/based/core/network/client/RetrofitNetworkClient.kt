package com.mtv.based.core.network.client

import com.mtv.based.core.network.config.NetworkConfigProvider
import com.mtv.based.core.network.model.NetworkResponse
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val apiService: RetrofitApi,
    config: NetworkConfigProvider,
) : NetworkClientInterface {

    val baseUrl = config.provide().baseUrl

    override suspend fun get(
        endpoint: String,
        query: Map<String, String>,
        headers: Map<String, String>
    ): NetworkResponse {
        val response = apiService.get(
            "${baseUrl}$endpoint",
            query,
            headers
        )

        if (!response.isSuccessful) throw HttpException(response)

        return NetworkResponse(
            body = response.body().orEmpty(),
            httpCode = response.code()
        )
    }

    override suspend fun post(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): NetworkResponse {
        val response = apiService.post(
            "${baseUrl}$endpoint",
            body,
            headers
        )

        if (!response.isSuccessful) throw HttpException(response)

        return NetworkResponse(
            body = response.body().orEmpty(),
            httpCode = response.code()
        )
    }

    override suspend fun put(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): NetworkResponse {
        val response = apiService.put(
            "$baseUrl$endpoint",
            body,
            headers
        )

        if (!response.isSuccessful) throw HttpException(response)

        return NetworkResponse(
            response.body().orEmpty(),
            response.code()
        )
    }

    override suspend fun delete(
        endpoint: String,
        headers: Map<String, String>
    ): NetworkResponse {
        val response = apiService.delete(
            "$baseUrl$endpoint",
            headers
        )

        if (!response.isSuccessful) throw HttpException(response)

        return NetworkResponse(
            response.body().orEmpty(),
            response.code()
        )
    }
}
