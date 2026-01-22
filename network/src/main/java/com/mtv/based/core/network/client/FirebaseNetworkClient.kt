package com.mtv.based.core.network.client

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.UiErrorFirebase
import com.mtv.based.core.network.utils.toFirebaseUiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.iterator
import kotlin.coroutines.cancellation.CancellationException

class FirebaseNetworkClient @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val config: FirebaseConfig
) : FirebaseDataSource {

    override fun <T> getDocument(
        collection: String,
        documentId: String,
        mapper: (Map<String, Any>) -> T
    ): Flow<ResourceFirebase<T>> = flow {
        emit(ResourceFirebase.Loading)

        try {
            val snapshot = firestore
                .collection(collection)
                .document(documentId)
                .get()
                .await()

            val data = snapshot.data
                ?: throw IllegalStateException(ErrorMessages.GENERIC_ERROR)

            emit(ResourceFirebase.Success(mapper(data)))
        } catch (e: Exception) {
            emit(ResourceFirebase.Error(e.toFirebaseUiError()))
        }
    }

    override fun <T> getDocumentByFields(
        collection: String,
        data: Map<String, Any>,
        mapper: (Map<String, Any>) -> T
    ): Flow<ResourceFirebase<T>> = flow {

        emit(ResourceFirebase.Loading)

        try {
            var query: Query = firestore.collection(collection)

            data.forEach { (key, value) ->
                query = query.whereEqualTo(key, value)
            }

            val snapshot = query.get().await()

            if (snapshot.isEmpty) {
                emit(
                    ResourceFirebase.Error(
                        UiErrorFirebase.NotFound(ErrorMessages.NOT_FOUND)
                    )
                )
                return@flow
            }

            val document = snapshot.documents.first()
            val map = (document.data ?: emptyMap()) + ("id" to document.id)
            emit(ResourceFirebase.Success(mapper(map)))

        } catch (e: Exception) {
            if (e is CancellationException) throw e
            emit(
                ResourceFirebase.Error(
                    UiErrorFirebase.Unknown(
                        e.message ?: ErrorMessages.GENERIC_ERROR
                    )
                )
            )
        }
    }


    override fun <T> getCollection(
        collection: String,
        mapper: (Map<String, Any>) -> T
    ): Flow<ResourceFirebase<List<T>>> = flow {
        emit(ResourceFirebase.Loading)

        try {
            val snapshot = firestore
                .collection(collection)
                .get()
                .await()

            val result = snapshot.documents.mapNotNull {
                it.data?.let(mapper)
            }

            emit(ResourceFirebase.Success(result))
        } catch (e: Exception) {
            emit(ResourceFirebase.Error(e.toFirebaseUiError()))
        }
    }

    override fun setDocument(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Flow<ResourceFirebase<Unit>> = flow {
        emit(ResourceFirebase.Loading)
        try {
            firestore
                .collection(collection)
                .document(documentId)
                .set(data)
                .await()

            emit(ResourceFirebase.Success(Unit))
        } catch (e: Exception) {
            emit(ResourceFirebase.Error(e.toFirebaseUiError()))
        }
    }

    override fun addDocument(
        collection: String,
        data: Map<String, Any>
    ): Flow<ResourceFirebase<String>> = flow {
        emit(ResourceFirebase.Loading)
        try {
            val documentRef = firestore
                .collection(collection)
                .add(data)
                .await()

            emit(ResourceFirebase.Success(documentRef.id))
        } catch (e: Exception) {
            emit(ResourceFirebase.Error(e.toFirebaseUiError()))
        }
    }


    override fun updateDocument(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Flow<ResourceFirebase<Unit>> = flow {
        emit(ResourceFirebase.Loading)
        try {
            firestore
                .collection(collection)
                .document(documentId)
                .update(data)
                .await()

            emit(ResourceFirebase.Success(Unit))
        } catch (e: Exception) {
            emit(ResourceFirebase.Error(e.toFirebaseUiError()))
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