package com.mtv.based.core.network.utils

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestoreException
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

fun Throwable.toFirebaseUiError(): UiErrorFirebase =
    when (this) {

        is FirebaseFirestoreException -> when (code) {
            FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                UiErrorFirebase.Permission(
                    message ?: ErrorMessages.PERMISSION_DENIED
                )

            FirebaseFirestoreException.Code.NOT_FOUND ->
                UiErrorFirebase.NotFound(
                    message ?: ErrorMessages.NOT_FOUND
                )

            FirebaseFirestoreException.Code.UNAVAILABLE ->
                UiErrorFirebase.Network(
                    message ?: ErrorMessages.NETWORK_ERROR
                )

            else ->
                UiErrorFirebase.Unknown(
                    message ?: ErrorMessages.GENERIC_ERROR
                )
        }

        is FirebaseException ->
            UiErrorFirebase.Network(
                message ?: ErrorMessages.NETWORK_ERROR
            )

        else ->
            UiErrorFirebase.Unknown(
                message ?: ErrorMessages.GENERIC_ERROR
            )
    }
