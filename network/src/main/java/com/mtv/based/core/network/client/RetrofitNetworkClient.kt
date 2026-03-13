package com.mtv.based.core.network.client

import com.mtv.based.core.network.config.NetworkConfigProvider
import com.mtv.based.core.network.datasource.NetworkDataSource
import com.mtv.based.core.network.datasource.RetrofitDataSource
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.model.RawNetworkResponse
import java.io.File
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

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

    override suspend fun patch(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = apiService.patch("$baseUrl$endpoint", body, headers)

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

    override suspend fun multipart(
        endpoint: String,
        parts: Map<String, Any>,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val multipartParts = parts.map { (key, value) ->
            when (value) {

                is File -> {
                    val requestFile = value
                        .asRequestBody("image/*".toMediaTypeOrNull())

                    MultipartBody.Part.createFormData(
                        key,
                        value.name,
                        requestFile
                    )
                }

                is String -> {
                    MultipartBody.Part.createFormData(key, value)
                }

                else -> {
                    throw IllegalArgumentException("Unsupported multipart type")
                }
            }
        }

        val response = apiService.multipart(
            "$baseUrl$endpoint",
            multipartParts,
            headers
        )

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

    override suspend fun download(
        endpoint: String,
        headers: Map<String, String>
    ): ByteArray {

        val response = apiService.download(
            "$baseUrl$endpoint",
            headers
        )

        if (!response.isSuccessful) {
            throw Exception("Download failed: ${response.code()}")
        }

        return response.body()?.bytes()
            ?: throw Exception("Empty response body")
    }

}
