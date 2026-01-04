package com.mtv.based.core.network.utils

import com.mtv.based.core.network.client.NetworkClientInterface
import com.mtv.based.core.network.client.NetworkClientSelector
import com.mtv.based.core.network.endpoint.ApiEndPoint
import com.mtv.based.core.network.header.HeaderMerger
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    selector: NetworkClientSelector,
    private val headerMerger: HeaderMerger
) {

    private val client: NetworkClientInterface = selector.get()

    suspend fun request(
        endpoint: ApiEndPoint,
        body: Any? = null,
        options: RequestOptions = RequestOptions()
    ): NetworkResponse {

        val finalOptions = options.copy(
            requireAuth = endpoint.requireAuth
        )

        val headers = headerMerger.build(finalOptions)

        return when (endpoint.method) {

            HttpMethod.Get -> client.get(
                endpoint = endpoint.path,
                query = finalOptions.query,
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
