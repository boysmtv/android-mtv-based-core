package com.mtv.based.core.network.usecase

import com.mtv.based.core.network.model.BaseResponse
import com.mtv.based.core.network.utils.NetworkResponse
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.UiError
import com.mtv.based.core.network.utils.toUiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract class BaseUseCase<P, T>(
    private val dispatcher: CoroutineDispatcher
) {

    abstract val serializer: KSerializer<T>

    operator fun invoke(param: P): Flow<Resource<T>> = flow {
        emit(Resource.Loading)

        try {
            val raw = execute(param)
            emit(handleResponse(raw))
        } catch (e: Throwable) {
            emit(Resource.Error(e.toUiError()))
        }
    }.flowOn(dispatcher)

    protected abstract suspend fun execute(param: P): NetworkResponse

    protected open fun handleResponse(raw: NetworkResponse): Resource<T> {
        return when (raw.httpCode) {
            in 200..299 -> {
                val baseResponse = Json.decodeFromString(
                    BaseResponse.serializer(serializer),
                    raw.body
                )

                baseResponse.data?.let { Resource.Success(it) }
                    ?: Resource.Error(UiError.Unknown(baseResponse.message))
            }

            401 -> Resource.Error(UiError.Unauthorized)
            403 -> Resource.Error(UiError.Forbidden)
            in 500..599 -> Resource.Error(UiError.Server)
            else -> Resource.Error(UiError.Unknown("HTTP ${raw.httpCode}"))
        }
    }
}


