package com.mtv.based.core.network.utils

sealed class UiErrorFirebase(
    open val message: String
) {
    data class Network(override val message: String) : UiErrorFirebase(message)
    data class Permission(override val message: String) : UiErrorFirebase(message)
    data class NotFound(override val message: String) : UiErrorFirebase(message)
    data class Unknown(override val message: String) : UiErrorFirebase(message)
    data class Validation(override val message: String) : UiErrorFirebase(message)
    data class RateLimit(override val message: String) : UiErrorFirebase(message)
}