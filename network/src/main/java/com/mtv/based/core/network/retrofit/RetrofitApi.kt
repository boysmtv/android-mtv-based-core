package com.mtv.based.core.network.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface RetrofitApi {
    @GET
    suspend fun getData(
        @Url url: String,
        @QueryMap(encoded = true) queries: Map<String, String> = emptyMap(),
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<String>

    @POST
    suspend fun postData(
        @Url url: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<String>
}