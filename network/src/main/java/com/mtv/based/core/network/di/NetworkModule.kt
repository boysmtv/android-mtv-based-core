package com.mtv.based.core.network.di

import com.mtv.based.core.network.ktor.KtorNetworkClient
import com.mtv.based.core.network.retrofit.RetrofitApi
import com.mtv.based.core.network.retrofit.RetrofitNetworkClient
import com.mtv.based.core.network.utils.NetworkClientInterface
import com.mtv.based.core.network.utils.NetworkConfigProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KtorClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKtorHttpClient(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        config: NetworkConfigProvider
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(config.provide().baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideRetrofitApi(retrofit: Retrofit): RetrofitApi =
        retrofit.create(RetrofitApi::class.java)

    @Provides
    @Singleton
    @KtorClient
    fun provideKtorNetworkClient(
        httpClient: HttpClient,
        config: NetworkConfigProvider
    ): NetworkClientInterface =
        KtorNetworkClient(httpClient, config)

    @Provides
    @Singleton
    @RetrofitClient
    fun provideRetrofitNetworkClient(
        apiService: RetrofitApi,
        config: NetworkConfigProvider
    ): NetworkClientInterface =
        RetrofitNetworkClient(apiService, config)
}
