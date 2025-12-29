package com.mtv.based.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDetail(
    val field: String,
    val message: String
)
