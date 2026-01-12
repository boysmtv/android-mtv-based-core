package com.mtv.based.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val field: String,
    val message: String
)
