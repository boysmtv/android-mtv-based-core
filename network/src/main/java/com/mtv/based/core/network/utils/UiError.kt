package com.mtv.based.core.network.utils

sealed interface UiError {
    val message: String

    data object Unauthorized : UiError {
        override val message = "Sesi anda telah berakhir"
    }

    data object Forbidden : UiError {
        override val message = "Akses ditolak"
    }

    data object Server : UiError {
        override val message = "Server sedang bermasalah"
    }

    data object Network : UiError {
        override val message = "Periksa koneksi internet"
    }

    data class Unknown(
        override val message: String
    ) : UiError
}
