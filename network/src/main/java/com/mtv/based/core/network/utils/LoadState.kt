/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: LoadState.kt
 *
 * Last modified by Dedy Wijaya on 25/03/26 14.19
 */

package com.mtv.based.core.network.utils

import com.mtv.based.core.network.utils.UiError

sealed interface LoadState<out T> {
    data object Idle : LoadState<Nothing>
    data object Loading : LoadState<Nothing>
    data class Success<T>(val data: T) : LoadState<T>
    data class Error(val error: UiError) : LoadState<Nothing>
}