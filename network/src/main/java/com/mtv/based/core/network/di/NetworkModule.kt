package com.mtv.based.core.network.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.mtv.based.core.network.client.KtorNetworkClient
import com.mtv.based.core.network.datasource.RetrofitDataSource
import com.mtv.based.core.network.client.RetrofitNetworkClient
import com.mtv.based.core.network.datasource.NetworkDataSource
import com.mtv.based.core.network.config.NetworkConfigProvider
import com.mtv.based.core.network.datasource.NetworkClientSelector
import com.mtv.based.core.network.header.HeaderMerger
import com.mtv.based.core.network.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
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
    fun provideKtorHttpClient(
        provider: NetworkConfigProvider
    ): HttpClient =
        HttpClient(CIO) {
            expectSuccess = false
            install(ContentNegotiation) {
                json()
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KtorLogger", message)
                    }
                }
                level = if (provider.provide().isDebug) LogLevel.ALL else LogLevel.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .redactHeaders("Authorization", "Cookie")
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        config: NetworkConfigProvider
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(config.provide().baseUrl)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideRetrofitApi(retrofit: Retrofit): RetrofitDataSource =
        retrofit.create(RetrofitDataSource::class.java)

    @Provides
    @Singleton
    @KtorClient
    fun provideKtorNetworkClient(
        httpClient: HttpClient,
        config: NetworkConfigProvider
    ): NetworkDataSource =
        KtorNetworkClient(httpClient, config)

    @Provides
    @Singleton
    @RetrofitClient
    fun provideRetrofitNetworkClient(
        apiService: RetrofitDataSource,
        config: NetworkConfigProvider
    ): NetworkDataSource =
        RetrofitNetworkClient(apiService, config)

    @Provides
    fun provideNetworkRepository(
        selector: NetworkClientSelector,
        headerMerger: HeaderMerger,
        json: Json
    ): NetworkRepository {
        return NetworkRepository(selector, headerMerger, json)
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = false
    }

}
