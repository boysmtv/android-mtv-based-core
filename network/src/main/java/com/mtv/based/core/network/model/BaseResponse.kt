package com.mtv.based.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val code: String,
    val message: String,
    val data: T? = null,
    val errors: List<ErrorDetail>? = null
)