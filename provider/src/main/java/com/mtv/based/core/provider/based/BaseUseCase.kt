package com.mtv.based.core.provider.based

import com.mtv.based.core.network.model.ApiErrorResponse
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.UiError
import com.mtv.based.core.network.utils.toUiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

abstract class BaseUseCase<P, T : Any>(
    private val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(param: P): Flow<Resource<T>> = flow {
        emit(Resource.Loading)

        try {
            val response = execute(param)
            emit(handleResponse(response))
        } catch (e: Throwable) {
            emit(Resource.Error(e.toUiError()))
        }

    }.flowOn(dispatcher)

    protected abstract suspend fun execute(param: P): NetworkResponse<T>

    protected open fun handleResponse(raw: NetworkResponse<T>): Resource<T> {

        val message = extractMessage(raw)

        return when (raw.httpCode) {

            in 200..299 -> {
                raw.data?.let { Resource.Success(it) }
                    ?: Resource.Error(
                        UiError.Unknown(
                            message ?: ErrorMessages.GENERIC_ERROR
                        )
                    )
            }

            400 -> Resource.Error(
                UiError.Validation(
                    message ?: ErrorMessages.INVALID_INPUT
                )
            )

            401 -> Resource.Error(
                UiError.Unauthorized(
                    message ?: ErrorMessages.SESSION_EXPIRED
                )
            )

            403 -> Resource.Error(
                UiError.Forbidden(
                    message ?: ErrorMessages.ACCESS_DENIED
                )
            )

            in 500..599 -> Resource.Error(
                UiError.Server(
                    message ?: ErrorMessages.SERVER_ERROR
                )
            )

            else -> Resource.Error(
                UiError.Unknown(
                    message ?: ErrorMessages.GENERIC_ERROR
                )
            )
        }
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private fun extractMessage(raw: NetworkResponse<*>): String? {
        return try {
            raw.rawBody?.let {
                json.decodeFromString(ApiErrorResponse.serializer(), it).message
            }
        } catch (e: Exception) {
            null
        }
    }
}
