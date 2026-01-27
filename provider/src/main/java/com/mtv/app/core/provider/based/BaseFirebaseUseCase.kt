package com.mtv.app.core.provider.based

import android.util.Log
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.toFirebaseUiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

abstract class BaseFirebaseUseCase<P, T : Any>(
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(param: P): Flow<ResourceFirebase<T>> {
        return execute(param)
            .onStart { emit(ResourceFirebase.Loading) }
            .catch { t ->
                Log.e("ERROR-BOYS", "BaseFirebaseUseCase")
                Log.e("ERROR-BOYS", "msg: " + t.message)
                Log.e("ERROR-BOYS", "msg: " + t.localizedMessage)
                emit(ResourceFirebase.Error(t.toFirebaseUiError()))
            }
            .flowOn(dispatcher)
    }

    protected abstract fun execute(param: P): Flow<ResourceFirebase<T>>
}