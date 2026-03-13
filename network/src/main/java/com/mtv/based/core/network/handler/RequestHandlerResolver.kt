/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: RequestHandlerResolver.kt
 *
 * Last modified by Dedy Wijaya on 03/03/26 20.50
 */

package com.mtv.based.core.network.handler

import com.mtv.based.core.network.endpoint.EndpointType
import javax.inject.Inject

class RequestHandlerResolver @Inject constructor(
    handlers: Set<@JvmSuppressWildcards RequestHandler>
) {

    private val map = handlers.associateBy { it.type }

    fun resolve(type: EndpointType): RequestHandler =
        map[type] ?: error("Handler not found for $type")

}