package com.mtv.based.core.network.firebase.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.mtv.based.core.network.firebase.config.FirebaseConfig
import com.mtv.based.core.network.firebase.result.FirebaseResult
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.toFirebaseUiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val config: FirebaseConfig
) : FirebaseDataSource {

    override fun <T> getDocument(
        collection: String,
        documentId: String,
        mapper: (Map<String, Any>) -> T
    ): Flow<FirebaseResult<T>> = flow {
        emit(FirebaseResult.Loading)

        try {
            val snapshot = firestore
                .collection(collection)
                .document(documentId)
                .get()
                .await()

            val data = snapshot.data
                ?: throw IllegalStateException(ErrorMessages.GENERIC_ERROR)

            emit(FirebaseResult.Success(mapper(data)))
        } catch (e: Exception) {
            emit(FirebaseResult.Error(e.toFirebaseUiError()))
        }
    }

    override fun <T> getCollection(
        collection: String,
        mapper: (Map<String, Any>) -> T
    ): Flow<FirebaseResult<List<T>>> = flow {
        emit(FirebaseResult.Loading)

        try {
            val snapshot = firestore
                .collection(collection)
                .get()
                .await()

            val result = snapshot.documents.mapNotNull {
                it.data?.let(mapper)
            }

            emit(FirebaseResult.Success(result))
        } catch (e: Exception) {
            emit(FirebaseResult.Error(e.toFirebaseUiError()))
        }
    }

    override fun setDocument(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Flow<FirebaseResult<Unit>> = flow {
        emit(FirebaseResult.Loading)
        try {
            firestore
                .collection(collection)
                .document(documentId)
                .set(data)
                .await()

            emit(FirebaseResult.Success(Unit))
        } catch (e: Exception) {
            emit(FirebaseResult.Error(e.toFirebaseUiError()))
        }
    }

    override fun addDocument(
        collection: String,
        data: Map<String, Any>
    ): Flow<FirebaseResult<String>> = flow {
        emit(FirebaseResult.Loading)
        try {
            val documentRef = firestore
                .collection(collection)
                .add(data)
                .await()

            emit(FirebaseResult.Success(documentRef.id))
        } catch (e: Exception) {
            emit(FirebaseResult.Error(e.toFirebaseUiError()))
        }
    }


    override fun updateDocument(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Flow<FirebaseResult<Unit>> = flow {
        emit(FirebaseResult.Loading)
        try {
            firestore
                .collection(collection)
                .document(documentId)
                .update(data)
                .await()

            emit(FirebaseResult.Success(Unit))
        } catch (e: Exception) {
            emit(FirebaseResult.Error(e.toFirebaseUiError()))
        }
    }

    override fun isExistByFields(
        collection: String,
        data: Map<String, Any>
    ): Flow<Boolean> = flow {
        for ((key, value) in data) {
            val snapshot = firestore.collection(collection)
                .whereEqualTo(key, value)
                .get()
                .await()

            if (!snapshot.isEmpty) {
                emit(true)
                return@flow
            }
        }
        emit(false)
    }

}