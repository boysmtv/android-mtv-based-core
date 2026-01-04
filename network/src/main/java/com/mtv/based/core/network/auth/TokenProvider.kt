package com.mtv.based.core.network.auth

interface TokenProvider {
    fun get(): String?
}