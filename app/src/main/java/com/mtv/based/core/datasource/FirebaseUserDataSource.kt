package com.mtv.based.core.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.mtv.based.core.network.config.FirebaseConfig
import javax.inject.Inject

class FirebaseUserDataSource @Inject constructor(
    private val config: FirebaseConfig,
    private val firestore: FirebaseFirestore
) {

    fun getUserCollection() = firestore.collection(config.defaultCollection)

}