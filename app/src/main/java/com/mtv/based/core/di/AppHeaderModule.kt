/*
 * Project: Boys.mtv@gmail.com
 * File: AppHeaderModule.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 21.18
 */

package com.mtv.based.core.di

import com.mtv.based.core.config.AppAdditionalHeaderProvider
import com.mtv.based.core.network.header.AdditionalHeaderProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppHeaderModule {

    @Provides
    fun provideAdditionalHeaderProvider(): AdditionalHeaderProvider =
        AppAdditionalHeaderProvider()


}
