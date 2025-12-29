package com.mtv.based.core.network.retrofit

import com.mtv.based.core.network.utils.NetworkConfig
import com.mtv.based.core.network.utils.NetworkClientInterface
import com.mtv.based.core.network.utils.NetworkResponse
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val apiService: RetrofitApi
) : NetworkClientInterface {

    override suspend fun get(
        endpoint: String,
        query: Map<String, String>,
        headers: Map<String, String>
    ): NetworkResponse {
        val response = apiService.getData(
            "${NetworkConfig.BASE_URL}$endpoint",
            query,
            headers
        )

        if (!response.isSuccessful) {
            throw HttpException(response)
        }

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
        val response = apiService.postData(
            "${NetworkConfig.BASE_URL}$endpoint",
            body,
            headers
        )

        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        return NetworkResponse(
            body = response.body().orEmpty(),
            httpCode = response.code()
        )
    }
}
