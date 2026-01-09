package com.mtv.based.core.network.firebase.result

import com.mtv.based.core.network.firebase.utils.FirebaseUiError

sealed class FirebaseResult<out T> {
    data object Loading : FirebaseResult<Nothing>()
    data class Success<T>(val data: T) : FirebaseResult<T>()
    data class Error(val error: FirebaseUiError) : FirebaseResult<Nothing>()
}
