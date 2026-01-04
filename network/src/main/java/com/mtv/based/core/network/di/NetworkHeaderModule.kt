/*
 * Project: Boys.mtv@gmail.com
 * File: NetworkHeaderModule.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 23.08
 */

package com.mtv.based.core.network.di
import com.mtv.based.core.network.header.AdditionalHeaderProvider
import com.mtv.based.core.network.header.BaseHeaderProvider
import com.mtv.based.core.network.header.DefaultBaseHeaderProvider
import com.mtv.based.core.network.header.EmptyAdditionalHeaderProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkHeaderModule {

    @Provides
    fun provideBaseHeaderProvider(): BaseHeaderProvider =
        DefaultBaseHeaderProvider()

    @Provides
    fun provideAdditionalHeaderProvider(): AdditionalHeaderProvider =
        EmptyAdditionalHeaderProvider()
}
