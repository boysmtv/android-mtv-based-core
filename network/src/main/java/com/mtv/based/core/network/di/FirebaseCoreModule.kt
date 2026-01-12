package com.mtv.based.core.network.di

import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseCoreModule {

    @Provides
    @Singleton
    fun provideFirestore(
        firebaseApp: FirebaseApp
    ): FirebaseFirestore {
        return FirebaseFirestore.getInstance(firebaseApp)
    }
}
