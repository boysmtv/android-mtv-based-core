package com.mtv.based.core.network.utils

data class RequestOptions(
    val headers: Map<String, String> = emptyMap(),
    val query: Map<String, String> = emptyMap(),
    val requireAuth: Boolean = true
)
