package com.mtv.based.core.network.utils

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestoreException
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toUiError(): UiError =
    when (this) {
        is IOException -> UiError.Network()
        is HttpException -> when (code()) {
            401 -> UiError.Unauthorized()
            403 -> UiError.Forbidden()
            in 500..599 -> UiError.Server()
            else -> UiError.Unknown(message())
        }

        else -> UiError.Unknown(message ?: ErrorMessages.GENERIC_ERROR)
    }

fun Throwable.toFirebaseUiError(): UiErrorFirebase =
    when (this) {
        is FirebaseFirestoreException -> when (code) {
            FirebaseFirestoreException.Code.PERMISSION_DENIED -> UiErrorFirebase.Permission(ErrorMessages.PERMISSION_DENIED)
            FirebaseFirestoreException.Code.UNAUTHENTICATED -> UiErrorFirebase.Permission(ErrorMessages.SESSION_EXPIRED)
            FirebaseFirestoreException.Code.NOT_FOUND -> UiErrorFirebase.NotFound(ErrorMessages.NOT_FOUND)
            FirebaseFirestoreException.Code.UNAVAILABLE,
            FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> UiErrorFirebase.Network(ErrorMessages.NETWORK_ERROR)

            FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED -> UiErrorFirebase.RateLimit(ErrorMessages.TOO_MANY_LOGIN_ATTEMPTS)
            FirebaseFirestoreException.Code.CANCELLED -> UiErrorFirebase.Unknown(ErrorMessages.GENERIC_ERROR)
            else -> UiErrorFirebase.Unknown(ErrorMessages.GENERIC_ERROR)
        }

        is FirebaseNetworkException -> UiErrorFirebase.Network(ErrorMessages.NETWORK_ERROR)
        is FirebaseException -> UiErrorFirebase.Unknown(ErrorMessages.GENERIC_ERROR)
        else -> UiErrorFirebase.Unknown(ErrorMessages.GENERIC_ERROR)
    }

fun mapFirebaseExceptionToUiError(e: Throwable): UiErrorFirebase = when (e) {
    is FirebaseAuthWeakPasswordException -> UiErrorFirebase.Validation(ErrorMessages.WEAK_PASSWORD)
    is FirebaseAuthInvalidCredentialsException -> UiErrorFirebase.Validation(ErrorMessages.INVALID_CREDENTIALS)
    is FirebaseAuthRecentLoginRequiredException -> UiErrorFirebase.Permission(ErrorMessages.RECENT_LOGIN_REQUIRED)
    is FirebaseAuthInvalidUserException -> UiErrorFirebase.Permission(ErrorMessages.SESSION_EXPIRED)
    is FirebaseNetworkException -> UiErrorFirebase.Network(ErrorMessages.NETWORK_ERROR)
    is FirebaseTooManyRequestsException -> UiErrorFirebase.RateLimit(ErrorMessages.TOO_MANY_LOGIN_ATTEMPTS)
    is FirebaseAuthUserCollisionException -> UiErrorFirebase.Validation(ErrorMessages.USER_ALREADY_EXISTS)
    is FirebaseAuthException -> when (e.errorCode) {
        "ERROR_INVALID_EMAIL" -> UiErrorFirebase.Validation(ErrorMessages.INVALID_EMAIL)
        "ERROR_USER_DISABLED" -> UiErrorFirebase.Permission(ErrorMessages.ACCESS_DENIED)
        "ERROR_USER_NOT_FOUND", "ERROR_EMAIL_NOT_FOUND" -> UiErrorFirebase.NotFound(ErrorMessages.NOT_FOUND)
        "ERROR_WRONG_PASSWORD" -> UiErrorFirebase.Validation(ErrorMessages.WRONG_PASSWORD)
        "ERROR_TOO_MANY_REQUESTS" -> UiErrorFirebase.RateLimit(ErrorMessages.TOO_MANY_LOGIN_ATTEMPTS)
        "ERROR_OPERATION_NOT_ALLOWED" -> UiErrorFirebase.Permission(ErrorMessages.AUTH_METHOD_DISABLED)
        "ERROR_REQUIRES_RECENT_LOGIN", "ERROR_USER_TOKEN_EXPIRED" -> UiErrorFirebase.Permission(ErrorMessages.SESSION_EXPIRED)
        "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> UiErrorFirebase.Validation(ErrorMessages.ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL)
        "ERROR_INTERNAL_ERROR", "ERROR_INVALID_API_KEY", "ERROR_APP_NOT_AUTHORIZED" -> UiErrorFirebase.Network(ErrorMessages.SERVER_ERROR)
        else -> UiErrorFirebase.Unknown(ErrorMessages.GENERIC_ERROR)
    }
    else -> UiErrorFirebase.Unknown(ErrorMessages.GENERIC_ERROR)
}
