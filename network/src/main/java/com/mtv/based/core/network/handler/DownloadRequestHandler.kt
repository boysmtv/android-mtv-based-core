/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: DownloadRequestHandler.kt
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

class DownloadRequestHandler @Inject constructor(
    private val retryPolicy: RetryPolicy
) : RequestHandler {

    override val type = EndpointType.Download

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> handle(
        client: NetworkDataSource,
        endpoint: IApiEndPoint,
        body: Any?,
        headers: Map<String, String>,
        serializer: KSerializer<T>?,
        options: RequestOptions
    ): NetworkResponse<T> {

        val bytes = retryPolicy.execute {
            client.download(
                endpoint = endpoint.path,
                headers = headers
            )
        }

        return NetworkResponse(
            httpCode = 200,
            data = bytes as T,
            rawBody = null
        )
    }
}