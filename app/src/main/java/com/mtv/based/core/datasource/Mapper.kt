package com.mtv.based.core.datasource

import com.mtv.based.core.model.UserDto

fun UserDto.checkFields(): Map<String, String> {
    return mapOf(
        "name" to this.name,
        "email" to this.email
    )
}