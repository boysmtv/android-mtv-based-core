package com.mtv.based.core.network.di

import com.mtv.based.core.network.header.AdditionalHeaderProvider
import dagger.BindsOptionalOf
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface OptionalHeaderModule {

    @BindsOptionalOf
    fun bindAdditionalHeaderProvider(): AdditionalHeaderProvider
}
