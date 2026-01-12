package com.mtv.based.core.network.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.client.FirebaseNetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseDataSourceModule {

    @Provides
    fun provideFirebaseDataSource(
        firestore: FirebaseFirestore,
        config: FirebaseConfig
    ): FirebaseDataSource = FirebaseNetworkClient(firestore, config)

}
