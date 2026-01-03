/*
 * Project: M2U ID
 * File: NetworkConfigModule.kt
 *
 * Created by PT. Maybank Indonesia Tbk,
 * Copyright © 2025, https://www.maybank.co.id/corporateinformation.
 *
 * Last modified by Dedy Wijaya on 31/12/2025 10.23
 */

package com.mtv.based.core.di

import com.mtv.based.core.config.AppNetworkConfigProvider
import com.mtv.based.core.network.utils.NetworkConfig
import com.mtv.based.core.network.utils.NetworkConfigProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppNetworkConfigModule {

    @Provides
    @Singleton
    fun provideNetworkConfigProvider(): NetworkConfigProvider = AppNetworkConfigProvider()

    @Provides
    @Singleton
    fun provideNetworkConfig(
        provider: NetworkConfigProvider
    ): NetworkConfig =
        provider.provide()

}
