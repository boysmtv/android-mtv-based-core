package com.mtv.based.core.network.firebase.usecase

import com.mtv.based.core.network.firebase.result.FirebaseResult
import com.mtv.based.core.network.utils.toFirebaseUiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

abstract class BaseFirebaseUseCase<P, T : Any>(
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(param: P): Flow<FirebaseResult<T>> {
        return execute(param)
            .onStart { emit(FirebaseResult.Loading) }
            .catch { t ->
                emit(FirebaseResult.Error(t.toFirebaseUiError()))
            }
            .flowOn(dispatcher)
    }

    protected abstract fun execute(param: P): Flow<FirebaseResult<T>>
}
