package com.mtv.based.core.di

import com.mtv.based.core.config.AppFirebaseConfigProvider
import com.mtv.based.core.network.firebase.config.FirebaseConfigProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppFirebaseConfigModule {

    @Provides
    fun provideFirebaseConfigProvider(): FirebaseConfigProvider = AppFirebaseConfigProvider()
}