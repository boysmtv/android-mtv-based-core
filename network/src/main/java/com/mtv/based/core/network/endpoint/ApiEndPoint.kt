package com.mtv.based.core.network.endpoint

import com.mtv.based.core.network.utils.HttpMethod

//object ApiEndPoint {
//
//    const val getUser = "name"
//
//    const val postUser = "user"
//
//}

sealed class ApiEndPoint(
    val path: String,
    val method: HttpMethod,
    val requireAuth: Boolean = true
) {

    object CreateUser : ApiEndPoint(
        path = "user/create",
        method = HttpMethod.Post,
        requireAuth = false
    )

    object GetUsers : ApiEndPoint(
        path = "users",
        method = HttpMethod.Get
    )

    data class GetUserDetail(
        val id: String
    ) : ApiEndPoint(
        path = "users/$id",
        method = HttpMethod.Get
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