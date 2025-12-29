package com.mtv.based.core.network.utils

import retrofit2.HttpException
import java.io.IOException

fun HttpException.toUiError(): UiError =
    when (code()) {
        401 -> UiError.Unauthorized
        403 -> UiError.Forbidden
        in 500..599 -> UiError.Server
        else -> UiError.Unknown(message())
    }

fun NetworkException.toUiError(): UiError =
    when (httpCode) {
        401 -> UiError.Unauthorized
        403 -> UiError.Forbidden
        in 500..599 -> UiError.Server
        else -> UiError.Unknown(message ?: "Network error")
    }

fun Throwable.toUiError(): UiError =
    when (this) {

        is NetworkException ->
            UiError.Network

        is IOException ->
            UiError.Network

        is HttpException -> {
            when (code()) {
                401 -> UiError.Unauthorized
                403 -> UiError.Forbidden
                in 500..599 -> UiError.Server
                else -> UiError.Unknown(message())
            }
        }

        else ->
            UiError.Unknown(message ?: "Unknown error")
    }
