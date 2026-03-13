/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: MultipartRequestHandler.kt
 *
 * Last modified by Dedy Wijaya on 03/03/26 20.49
 */

package com.mtv.based.core.network.handler

import com.mtv.based.core.network.datasource.NetworkDataSource
import com.mtv.based.core.network.endpoint.EndpointType
import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.model.RequestOptions
import com.mtv.based.core.network.policy.RetryPolicy
import javax.inject.Inject
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class MultipartRequestHandler @Inject constructor(
    private val json: Json,
    private val retryPolicy: RetryPolicy
) : RequestHandler {

    override val type = EndpointType.Multipart

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> handle(
        client: NetworkDataSource,
        endpoint: IApiEndPoint,
        body: Any?,
        headers: Map<String, String>,
        serializer: KSerializer<T>?,
        options: RequestOptions
    ): NetworkResponse<T> {

        require(body is Map<*, *>) {
            "Multipart body must be Map<String, Any>"
        }

        val raw = retryPolicy.execute {
            client.multipart(
                endpoint = endpoint.path,
                parts = body as Map<String, Any>,
                headers = headers
            )
        }

        val parsed = if (raw.httpCode in 200..299) {
            serializer?.let {
                json.decodeFromString(it, raw.body)
            }
        } else {
            null
        }

        return NetworkResponse(
            httpCode = raw.httpCode,
            data = parsed,
            rawBody = raw.body
        )
    }

}