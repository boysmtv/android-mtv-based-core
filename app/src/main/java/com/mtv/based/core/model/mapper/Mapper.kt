package com.mtv.based.core.model.mapper

import com.mtv.based.core.model.UserDto

fun UserDto.toFirebaseMap(): Map<String, Any> = mapOf(
    "name" to name,
    "email" to email
)