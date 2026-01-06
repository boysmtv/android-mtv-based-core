package com.mtv.based.core.network.firebase.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mtv.based.core.network.firebase.config.FirebaseConfig
import com.mtv.based.core.network.firebase.datasource.FirebaseDataSource
import com.mtv.based.core.network.firebase.datasource.FirebaseDataSourceImpl
import dagger.Binds
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
    ): FirebaseDataSource = FirebaseDataSourceImpl(firestore, config)

}
