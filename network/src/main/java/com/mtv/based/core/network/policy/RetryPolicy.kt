/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: RetryPolicy.kt
 *
 * Last modified by Dedy Wijaya on 03/03/26 20.35
 */

package com.mtv.based.core.network.policy

import kotlinx.coroutines.delay
import javax.inject.Inject

class RetryPolicy @Inject constructor() {

    suspend fun <T> execute(
        times: Int = 2,
        delayMillis: Long = 0,
        block: suspend () -> T
    ): T {

        repeat(times - 1) {
            try {
                return block()
            } catch (e: Exception) {
                if (delayMillis > 0) delay(delayMillis)
            }
        }

        return block()
    }
}