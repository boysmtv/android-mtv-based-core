package com.mtv.based.core.network.utils

sealed class ResourceFirebase<out T> {
    data object Loading : ResourceFirebase<Nothing>()
    data class Success<T>(val data: T) : ResourceFirebase<T>()
    data class Error(val error: UiErrorFirebase) : ResourceFirebase<Nothing>()
}