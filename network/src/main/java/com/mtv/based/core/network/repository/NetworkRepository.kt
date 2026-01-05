package com.mtv.based.core.network.repository

import com.mtv.based.core.network.client.NetworkClientInterface
import com.mtv.based.core.network.client.NetworkClientSelector
import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.header.HeaderMerger
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.model.RequestOptions
import com.mtv.based.core.network.utils.HttpMethod
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    selector: NetworkClientSelector,
    private val headerMerger: HeaderMerger
) {

    private val client: NetworkClientInterface = selector.get()

    suspend fun request(
        endpoint: IApiEndPoint,
        body: Any? = null,
        options: RequestOptions = RequestOptions()
    ): NetworkResponse {

        val headers = headerMerger.build()

        return when (endpoint.method) {

            HttpMethod.Get -> client.get(
                endpoint = endpoint.path,
                query = options.query,
                headers = headers
            )

            HttpMethod.Post -> client.post(
                endpoint = endpoint.path,
                body = body ?: Unit,
                headers = headers
            )

            HttpMethod.Put -> client.put(
                endpoint = endpoint.path,
                body = body ?: Unit,
                headers = headers
            )

            HttpMethod.Delete -> client.delete(
                endpoint = endpoint.path,
                headers = headers
            )
        }
    }

}