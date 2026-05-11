/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: Mapper.kt
 *
 * Last modified by Dedy Wijaya on 23/03/26 13.13
 */

package com.mtv.based.core.provider.utils

import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.UiError
import com.mtv.based.core.network.utils.UiErrorFirebase

fun UiErrorFirebase.toUiError(): UiError {
    return when (this) {
        is UiErrorFirebase.Network -> UiError.Network(message)
        is UiErrorFirebase.Permission -> UiError.Forbidden(message)
        is UiErrorFirebase.NotFound -> UiError.Validation(message)
        is UiErrorFirebase.Validation -> UiError.Validation(message)
        is UiErrorFirebase.RateLimit -> UiError.Server(message)
        is UiErrorFirebase.Unknown -> UiError.Unknown(message)
    }
}

fun <T> ResourceFirebase<T>.toResource(): Resource<T> {
    return when (this) {
        is ResourceFirebase.Loading -> Resource.Loading
        is ResourceFirebase.Success -> Resource.Success(data)
        is ResourceFirebase.Error -> Resource.Error(error.toUiError())
    }
}