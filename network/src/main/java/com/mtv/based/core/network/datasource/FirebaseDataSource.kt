package com.mtv.based.core.network.datasource

import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {

    fun <T> getDocument(
        collection: String,
        documentId: String,
        mapper: (Map<String, Any>) -> T
    ): Flow<ResourceFirebase<T>>

    fun <T> getDocumentByFields(
        collection: String,
        data: Map<String, Any>,
        mapper: (Map<String, Any>) -> T
    ): Flow<ResourceFirebase<T>>

    fun <T> getCollection(
        collection: String,
        mapper: (Map<String, Any>) -> T
    ): Flow<ResourceFirebase<List<T>>>

    fun setDocument(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Flow<ResourceFirebase<Unit>>

    fun addDocument(
        collection: String,
        data: Map<String, Any>
    ): Flow<ResourceFirebase<String>>

    fun updateDocument(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Flow<ResourceFirebase<Unit>>

    fun isExistByFields(
        collection: String,
        data: Map<String, Any>
    ): Flow<Boolean>
}