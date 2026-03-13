package com.mtv.based.core.network.repository

import com.mtv.based.core.network.datasource.NetworkClientSelector
import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.handler.RequestHandlerResolver
import com.mtv.based.core.network.header.HeaderMerger
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.model.RequestOptions
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    val selector: NetworkClientSelector,
    val headerMerger: HeaderMerger,
    val resolver: RequestHandlerResolver
) {

    suspend inline fun <reified T : Any> request(
        endpoint: IApiEndPoint,
        body: Any? = null,
        options: RequestOptions = RequestOptions()
    ): NetworkResponse<T> {

        val client = selector.get()
        val headers = headerMerger.build()
        val handler = resolver.resolve(endpoint.type)

        return handler.handle(
            client = client,
            endpoint = endpoint,
            body = body,
            headers = headers,
            serializer = kotlinx.serialization.serializer(),
            options = options
        )
    }
}