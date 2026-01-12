package com.mtv.based.core.network.datasource

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface RetrofitDataSource {

    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap(encoded = true) queries: Map<String, String> = emptyMap(),
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<String>

    @POST
    suspend fun post(
        @Url url: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<String>

    @PUT
    suspend fun put(
        @Url url: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<String>

    @DELETE
    suspend fun delete(
        @Url url: String,
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<String>

}