package com.mtv.based.core.network.firebase.utils

sealed class FirebaseUiError(
    open val message: String
) {
    data class Network(override val message: String) : FirebaseUiError(message)
    data class Permission(override val message: String) : FirebaseUiError(message)
    data class NotFound(override val message: String) : FirebaseUiError(message)
    data class Unknown(override val message: String) : FirebaseUiError(message)
}
