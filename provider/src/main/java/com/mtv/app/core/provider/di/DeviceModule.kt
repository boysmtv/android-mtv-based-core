package com.mtv.app.core.provider.di

import android.content.Context
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.core.provider.utils.device.DeviceInfoProvider
import com.mtv.app.core.provider.utils.device.InstallationIdProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeviceModule {

    @Provides
    @Singleton
    fun provideInstallationIdProvider(prefs: SecurePrefs): InstallationIdProvider {
        return InstallationIdProvider(prefs)
    }

    @Provides
    @Singleton
    fun provideDeviceInfoProvider(
        @ApplicationContext context: Context,
        installationIdProvider: InstallationIdProvider
    ): DeviceInfoProvider {
        return DeviceInfoProvider(context, installationIdProvider)
    }

}