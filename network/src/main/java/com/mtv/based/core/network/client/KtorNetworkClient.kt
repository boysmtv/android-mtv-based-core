package com.mtv.based.core.network.client

import com.mtv.based.core.network.utils.NetworkConfigProvider
import com.mtv.based.core.network.utils.NetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class KtorNetworkClient @Inject constructor(
    private val client: HttpClient,
    config: NetworkConfigProvider,
) : NetworkClientInterface {

    val baseUrl = config.provide().baseUrl

    override suspend fun get(
        endpoint: String,
        query: Map<String, String>,
        headers: Map<String, String>
    ): NetworkResponse {
        val response = client.get("${baseUrl}$endpoint") {
            query.forEach { (k, v) -> parameter(k, v) }
            headers.forEach { (k, v) -> header(k, v) }
        }

        return NetworkResponse(
            body = response.bodyAsText(),
            httpCode = response.status.value
        )
    }

    override suspend fun post(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): NetworkResponse {
        val response = client.post("${baseUrl}$endpoint") {
            contentType(ContentType.Application.Json)
            setBody(body)
            headers.forEach { (k, v) -> header(k, v) }
        }

        return NetworkResponse(
            body = response.bodyAsText(),
            httpCode = response.status.value
        )
    }

    override suspend fun put(
        endpoint: String,
        body: Any,
        headers: Map<String, String>
    ): NetworkResponse {
        val response = client.put("$baseUrl$endpoint") {
            contentType(ContentType.Application.Json)
            setBody(body)
            headers.forEach { (k, v) -> header(k, v) }
        }

        return NetworkResponse(
            body = response.bodyAsText(),
            httpCode = response.status.value
        )
    }

    override suspend fun delete(
        endpoint: String,
        headers: Map<String, String>
    ): NetworkResponse {
        val response = client.delete("$baseUrl$endpoint") {
            headers.forEach { (k, v) -> header(k, v) }
        }

        return NetworkResponse(
            body = response.bodyAsText(),
            httpCode = response.status.value
        )
    }
}