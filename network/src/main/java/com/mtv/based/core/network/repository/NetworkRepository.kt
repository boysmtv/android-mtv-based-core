package com.mtv.based.core.network.repository

import com.mtv.based.core.network.datasource.NetworkDataSource
import com.mtv.based.core.network.datasource.NetworkClientSelector
import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.header.HeaderMerger
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.model.RequestOptions
import com.mtv.based.core.network.utils.HttpMethod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    selector: NetworkClientSelector,
    private val headerMerger: HeaderMerger,
    private val json: Json
) {

    private val client = selector.get()

    suspend fun <T> request(
        endpoint: IApiEndPoint,
        body: Any? = null,
        options: RequestOptions = RequestOptions(),
        serializer: KSerializer<T>
    ): NetworkResponse<T> {

        val headers = headerMerger.build()

        val raw = when (endpoint.method) {
            HttpMethod.Get -> client.get(endpoint.path, options.query, headers)
            HttpMethod.Post -> client.post(endpoint.path, body ?: Unit, headers)
            HttpMethod.Put -> client.put(endpoint.path, body ?: Unit, headers)
            HttpMethod.Delete -> client.delete(endpoint.path, headers)
        }

        val parsed = runCatching {
            json.decodeFromString(serializer, raw.body)
        }.getOrNull()

        return NetworkResponse(
            httpCode = raw.httpCode,
            data = parsed,
            rawBody = raw.body
        )
    }
}

