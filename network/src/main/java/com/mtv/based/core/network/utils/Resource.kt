package com.mtv.based.core.network.utils

sealed interface Resource<out T> {

    data object Idle : Resource<Nothing>

    data object Loading : Resource<Nothing>

    data class Success<T>(
        val data: T
    ) : Resource<T>

    data class Error(
        val error: UiError
    ) : Resource<Nothing>

}
