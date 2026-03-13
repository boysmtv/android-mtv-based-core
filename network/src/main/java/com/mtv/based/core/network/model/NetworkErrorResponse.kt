/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: NetworkErrorResponse.kt
 *
 * Last modified by Dedy Wijaya on 07/03/26 02.41
 */

package com.mtv.based.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val message: String? = null
)