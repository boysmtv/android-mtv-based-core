/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: RequestHandler.kt
 *
 * Last modified by Dedy Wijaya on 03/03/26 20.48
 */

package com.mtv.based.core.network.handler

import com.mtv.based.core.network.datasource.NetworkDataSource
import com.mtv.based.core.network.endpoint.EndpointType
import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.model.RequestOptions
import kotlinx.serialization.KSerializer

interface RequestHandler {

    val type: EndpointType

    suspend fun <T> handle(
        client: NetworkDataSource,
        endpoint: IApiEndPoint,
        body: Any?,
        headers: Map<String, String>,
        serializer: KSerializer<T>?,
        options: RequestOptions
    ): NetworkResponse<T>

}