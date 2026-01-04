package com.mtv.based.core.network.header

interface AdditionalHeaderProvider {
    fun provide(): Map<String, String>
}