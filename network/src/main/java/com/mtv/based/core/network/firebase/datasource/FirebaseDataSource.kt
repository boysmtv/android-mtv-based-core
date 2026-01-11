package com.mtv.based.core.network.firebase.datasource


import com.mtv.based.core.network.firebase.result.FirebaseResult
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {

    fun <T> getDocument(
        collection: String,
        documentId: String,
        mapper: (Map<String, Any>) -> T
    ): Flow<FirebaseResult<T>>

    fun <T> getDocumentByFields(
        collection: String,
        data: Map<String, Any>,
        mapper: (Map<String, Any>) -> T
    ): Flow<FirebaseResult<T>>

    fun <T> getCollection(
        collection: String,
        mapper: (Map<String, Any>) -> T
    ): Flow<FirebaseResult<List<T>>>

    fun setDocument(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Flow<FirebaseResult<Unit>>

    fun addDocument(
        collection: String,
        data: Map<String, Any>
    ): Flow<FirebaseResult<String>>

    fun updateDocument(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Flow<FirebaseResult<Unit>>

    fun isExistByFields(
        collection: String,
        data: Map<String, Any>
    ): Flow<Boolean>
}