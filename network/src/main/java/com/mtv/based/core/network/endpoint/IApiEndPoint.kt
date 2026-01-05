package com.mtv.based.core.network.endpoint

import com.mtv.based.core.network.utils.HttpMethod

interface IApiEndPoint {
    val path: String
    val method: HttpMethod
}
