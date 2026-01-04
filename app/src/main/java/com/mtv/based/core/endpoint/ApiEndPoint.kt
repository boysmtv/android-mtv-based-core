package com.mtv.based.core.endpoint

import com.mtv.based.core.network.utils.HttpMethod

sealed class ApiEndPoint(
    val path: String,
    val method: HttpMethod,
) {

    object GetUsers : ApiEndPoint(
        path = "api/name",
        method = HttpMethod.Get
    )

    object CreateUser : ApiEndPoint(
        path = "user/create",
        method = HttpMethod.Post,
    )

    data class UpdateUser(
        val id: String
    ) : ApiEndPoint(
        path = "users/$id",
        method = HttpMethod.Put
    )

    data class DeleteUser(
        val id: String
    ) : ApiEndPoint(
        path = "users/$id",
        method = HttpMethod.Delete
    )

}