package com.mtv.based.core.network.usecase

data class UseCaseResult<T>(
    val data: T,
    val httpCode: Int
)

