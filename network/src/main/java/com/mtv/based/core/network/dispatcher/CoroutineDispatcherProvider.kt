/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: CoroutineDispatcherProvider.kt
 *
 * Last modified by Dedy Wijaya on 23/03/26 01.34
 */

package com.mtv.based.core.network.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatcherProvider {
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}