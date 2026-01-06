package com.mtv.based.core.network.utils

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
