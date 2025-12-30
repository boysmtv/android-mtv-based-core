package com.mtv.based.core.network.utils

import android.graphics.Mesh

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

    data class Network(var error: String) : UiError {
        override val message = "Periksa koneksi internet - error"
    }

    data class IO(
        override val message: String
    ) : UiError

    data class Unknown(
        override val message: String
    ) : UiError
}
