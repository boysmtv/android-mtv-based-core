/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: AuthInterceptor.kt
 *
 * Last modified by Dedy Wijaya on 19/03/26 01.37
 */

package com.mtv.based.core.network.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val onUnauthorized: (suspend () -> Unit)? = null
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code == 401) {
            onUnauthorized?.let {
                runBlocking { it() }
            }
        }

        return response
    }
}