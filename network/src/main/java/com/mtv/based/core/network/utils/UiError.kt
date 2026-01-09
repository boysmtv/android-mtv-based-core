package com.mtv.based.core.network.utils

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.mtv.based.core.network.firebase.utils.FirebaseUiError

sealed interface UiError {
    val message: String

    data class Validation(
        override val message: String
    ) : UiError

    data class Network(
        override val message: String = ErrorMessages.NETWORK_ERROR
    ) : UiError

    data class Unauthorized(
        override val message: String = ErrorMessages.SESSION_EXPIRED
    ) : UiError

    data class Forbidden(
        override val message: String = ErrorMessages.ACCESS_DENIED
    ) : UiError

    data class Server(
        override val message: String = ErrorMessages.SERVER_ERROR
    ) : UiError

    data class Unknown(
        override val message: String = ErrorMessages.GENERIC_ERROR
    ) : UiError
}

fun Throwable.toFirebaseUiError(): FirebaseUiError =
    when (this) {

        is FirebaseFirestoreException -> when (code) {
            FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                FirebaseUiError.Permission(
                    message ?: ErrorMessages.PERMISSION_DENIED
                )

            FirebaseFirestoreException.Code.NOT_FOUND ->
                FirebaseUiError.NotFound(
                    message ?: ErrorMessages.NOT_FOUND
                )

            FirebaseFirestoreException.Code.UNAVAILABLE ->
                FirebaseUiError.Network(
                    message ?: ErrorMessages.NETWORK_ERROR
                )

            else ->
                FirebaseUiError.Unknown(
                    message ?: ErrorMessages.GENERIC_ERROR
                )
        }

        is FirebaseException ->
            FirebaseUiError.Network(
                message ?: ErrorMessages.NETWORK_ERROR
            )

        else ->
            FirebaseUiError.Unknown(
                message ?: ErrorMessages.GENERIC_ERROR
            )
    }
