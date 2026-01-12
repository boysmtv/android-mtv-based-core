package com.mtv.based.core.network.di

import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.config.FirebaseConfigProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppFirebaseModule {

    @Provides
    fun provideFirebaseConfig(
        provider: FirebaseConfigProvider
    ): FirebaseConfig = provider.provide()

}
