package com.mtv.based.core.network.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.mtv.based.core.network.client.KtorNetworkClient
import com.mtv.based.core.network.client.RetrofitNetworkClient
import com.mtv.based.core.network.config.NetworkConfigProvider
import com.mtv.based.core.network.datasource.NetworkClientSelector
import com.mtv.based.core.network.datasource.NetworkDataSource
import com.mtv.based.core.network.datasource.RetrofitDataSource
import com.mtv.based.core.network.handler.RequestHandlerResolver
import com.mtv.based.core.network.header.HeaderMerger
import com.mtv.based.core.network.interceptor.AuthInterceptor
import com.mtv.based.core.network.policy.RetryPolicy
import com.mtv.based.core.network.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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
        provider: NetworkConfigProvider,
        json: Json
    ): HttpClient =
        HttpClient(CIO) {

            expectSuccess = false

            install(ContentNegotiation) {
                json(json)
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
        context: Context,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("RetrofitNetwork", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .redactHeaders("Authorization", "Cookie")
                    .build()
            )
            .addInterceptor(loggingInterceptor)
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
        resolver: RequestHandlerResolver
    ): NetworkRepository {
        return NetworkRepository(
            selector = selector,
            headerMerger = headerMerger,
            resolver = resolver
        )
    }

    @Provides
    @Singleton
    fun provideRetryPolicy(): RetryPolicy = RetryPolicy()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = false
    }

}
