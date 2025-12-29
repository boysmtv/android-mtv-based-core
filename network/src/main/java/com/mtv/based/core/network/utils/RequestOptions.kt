package com.mtv.based.core.network.utils

data class RequestOptions(
    val query: Map<String, String> = emptyMap(),
    val headers: Map<String, String> = emptyMap(),
)
