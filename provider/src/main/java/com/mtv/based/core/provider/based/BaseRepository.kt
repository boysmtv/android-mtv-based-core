package com.mtv.based.core.provider.based

import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.provider.utils.flowResource
import com.mtv.based.core.provider.utils.safeApiCall
import com.mtv.based.core.provider.utils.safeFirebaseCall
import kotlinx.coroutines.flow.Flow

abstract class BaseRepository {

    protected fun <T> apiFlow(
        call: suspend () -> T
    ): Flow<Resource<T>> = flowResource {
        safeApiCall { call() }
    }

    protected fun <T> firebaseFlow(
        call: suspend () -> T
    ): Flow<Resource<T>> = flowResource {
        safeFirebaseCall { call() }
    }
}