/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: ResultState.kt
 *
 * Last modified by Dedy Wijaya on 23/03/26 13.12
 */

package com.mtv.based.core.provider.utils

sealed class ResultState<T> {
    object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val message: String) : ResultState<Nothing>()
}