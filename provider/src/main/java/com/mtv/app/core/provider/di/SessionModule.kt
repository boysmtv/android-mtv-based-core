package com.mtv.app.core.provider.di

import android.content.Context
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.core.provider.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideSecurePrefs(@ApplicationContext context: Context): SecurePrefs {
        return SecurePrefs(context)
    }

    @Provides
    @Singleton
    fun provideSessionManager(securePrefs: SecurePrefs): SessionManager {
        return SessionManager(securePrefs)
    }

}
