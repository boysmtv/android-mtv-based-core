package com.mtv.based.core.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val firstname: String,
    val lastname: String,
    val lastLogin: String,
)