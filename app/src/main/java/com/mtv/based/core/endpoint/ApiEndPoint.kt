package com.mtv.based.core.endpoint

import com.mtv.based.core.model.LoginRequest
import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.utils.HttpMethod

class ApiEndPoint {

    object GetUsers : IApiEndPoint {
        override val path = "api/name"
        override val method = HttpMethod.Get
    }

    object AuthLogin : IApiEndPoint {
        override val path = "auth/login"
        override val method = HttpMethod.Post
    }

}