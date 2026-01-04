package com.mtv.based.core.network.header

interface AdditionalHeaderProvider {
    fun provide(requireAuth: Boolean): Map<String, String>
}