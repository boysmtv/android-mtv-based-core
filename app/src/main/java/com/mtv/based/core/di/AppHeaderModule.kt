/*
 * Project: Boys.mtv@gmail.com
 * File: AppHeaderModule.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 21.18
 */

package com.mtv.based.core.di

import com.mtv.based.core.config.AppAdditionalHeaderProviderImpl
import com.mtv.based.core.network.header.AppAdditionalHeaderProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppHeaderModule {

    @Binds
    abstract fun bindAdditionalHeaderProvider(
        impl: AppAdditionalHeaderProviderImpl
    ): AppAdditionalHeaderProvider
}
