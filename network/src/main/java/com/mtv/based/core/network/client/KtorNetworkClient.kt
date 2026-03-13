package com.mtv.based.core.network.client

import com.mtv.based.core.network.config.NetworkConfigProvider
import com.mtv.based.core.network.datasource.NetworkDataSource
import com.mtv.based.core.network.model.RawNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.toByteArray
import javax.inject.Inject

class KtorNetworkClient @Inject constructor(
    private val client: HttpClient,
    config: NetworkConfigProvider,
) : NetworkDataSource {

    private val baseUrl = config.provide().baseUrl

    override suspend fun get(
        endpoint: String,
        query: Map<String, String>,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = client.get("$baseUrl$endpoint") {
            query.forEach { (k, v) -> parameter(k, v) }
            headers.forEach { (k, v) -> header(k, v) }
        }

        return RawNetworkResponse(
            httpCode = response.status.value,
            body = response.bodyAsText()
        )
    }

    override suspend fun post(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = client.post("$baseUrl$endpoint") {
            contentType(ContentType.Application.Json)
            setBody(body)
            headers.forEach { (k, v) -> header(k, v) }
        }

        return RawNetworkResponse(
            httpCode = response.status.value,
            body = response.bodyAsText()
        )
    }

    override suspend fun put(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = client.put("$baseUrl$endpoint") {
            contentType(ContentType.Application.Json)
            setBody(body)
            headers.forEach { (k, v) -> header(k, v) }
        }

        return RawNetworkResponse(
            httpCode = response.status.value,
            body = response.bodyAsText()
        )
    }

    override suspend fun patch(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = client.patch("$baseUrl$endpoint") {
            contentType(ContentType.Application.Json)
            setBody(body)
            headers.forEach { (k, v) -> header(k, v) }
        }

        return RawNetworkResponse(
            httpCode = response.status.value,
            body = response.bodyAsText()
        )
    }

    override suspend fun delete(
        endpoint: String,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = client.delete("$baseUrl$endpoint") {
            headers.forEach { (k, v) -> header(k, v) }
        }

        return RawNetworkResponse(
            httpCode = response.status.value,
            body = response.bodyAsText()
        )
    }

    override suspend fun multipart(
        endpoint: String,
        parts: Map<String, Any>,
        headers: Map<String, String>
    ): RawNetworkResponse {

        val response = client.post("$baseUrl$endpoint") {
            setBody(
                MultiPartFormDataContent(
                formData {
                    parts.forEach { (key, value) ->
                        when (value) {
                            is ByteArray -> append(key, value)
                            is String -> append(key, value)
                        }
                    }
                }
            ))
            headers.forEach { (k, v) -> header(k, v) }
        }

        return RawNetworkResponse(
            httpCode = response.status.value,
            body = response.bodyAsText()
        )
    }

    override suspend fun download(
        endpoint: String,
        headers: Map<String, String>
    ): ByteArray {

        val response = client.get("$baseUrl$endpoint") {
            headers.forEach { (k, v) -> header(k, v) }
        }

        return response.bodyAsChannel().toByteArray()
    }
}
