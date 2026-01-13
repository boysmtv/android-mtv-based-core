package com.mtv.based.core.network.model

data class NetworkResponse<T>(
    val httpCode: Int,
    val data: T? = null,
    val rawBody: String? = null
)