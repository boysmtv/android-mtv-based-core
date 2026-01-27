package com.mtv.app.core.provider.based

import android.util.Log
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.UiError
import com.mtv.based.core.network.utils.toUiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<P, T : Any>(
    private val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(param: P): Flow<Resource<T>> = flow {
        emit(Resource.Loading)

        try {
            val response = execute(param)
            emit(handleResponse(response))
        } catch (e: Throwable) {
            Log.e("ERROR-BOYS", "BaseUseCase")
            Log.e("ERROR-BOYS", "msg: " + e.message)
            Log.e("ERROR-BOYS", "msg: " + e.localizedMessage)
            emit(Resource.Error(e.toUiError()))
        }

    }.flowOn(dispatcher)

    /**
     * Implementasi use case akan selalu mengembalikan NetworkResponse<T>
     */
    protected abstract suspend fun execute(param: P): NetworkResponse<T>

    /**
     * Default handler dari NetworkResponse ke Resource
     * Bisa di override jika perlu custom behavior
     */
    protected open fun handleResponse(raw: NetworkResponse<T>): Resource<T> {
        return when (raw.httpCode) {

            in 200..299 -> {
                raw.data?.let { Resource.Success(it) }
                    ?: Resource.Error(UiError.Unknown(ErrorMessages.GENERIC_ERROR))
            }

            400 -> Resource.Error(
                UiError.Validation(
                    raw.rawBody ?: ErrorMessages.INVALID_INPUT
                )
            )

            401 -> Resource.Error(
                UiError.Unauthorized(
                    raw.rawBody ?: ErrorMessages.SESSION_EXPIRED
                )
            )

            403 -> Resource.Error(
                UiError.Forbidden(
                    raw.rawBody ?: ErrorMessages.ACCESS_DENIED
                )
            )

            in 500..599 -> Resource.Error(
                UiError.Server(
                    raw.rawBody ?: ErrorMessages.SERVER_ERROR
                )
            )

            else -> Resource.Error(
                UiError.Unknown(
                    raw.rawBody ?: ErrorMessages.GENERIC_ERROR
                )
            )
        }
    }
}
