package com.mtv.based.core.network.firebase.di

import com.mtv.based.core.network.firebase.config.FirebaseConfig
import com.mtv.based.core.network.firebase.config.FirebaseConfigProvider
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
