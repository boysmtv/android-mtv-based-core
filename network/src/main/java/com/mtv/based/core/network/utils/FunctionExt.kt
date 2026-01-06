package com.mtv.based.core.network.utils

import retrofit2.HttpException
import java.io.IOException

fun Throwable.toUiError(): UiError =
    when (this) {

        is IOException ->
            UiError.Network()

        is HttpException -> {
            when (code()) {
                401 -> UiError.Unauthorized()
                403 -> UiError.Forbidden()
                in 500..599 -> UiError.Server()
                else -> UiError.Unknown(message())
            }
        }

        else ->
            UiError.Unknown(message ?: ErrorMessages.GENERIC_ERROR)
    }
