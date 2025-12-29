package com.mtv.based.core.network.utils

class NetworkException(
    val httpCode: Int,
    message: String
) : Exception(message)
