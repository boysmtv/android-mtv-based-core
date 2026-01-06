package com.mtv.based.core.network.usecase

import com.mtv.based.core.network.model.BaseResponse
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.UiError
import com.mtv.based.core.network.utils.toUiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

abstract class BaseUseCase<P, T : Any>(
    private val dispatcher: CoroutineDispatcher,
    clazz: KClass<T>
) {

    @OptIn(InternalSerializationApi::class)
    protected val serializer: KSerializer<T> = clazz.serializer()

    protected val json = Json { ignoreUnknownKeys = true }

    operator fun invoke(param: P): Flow<Resource<T>> = flow {
        emit(Resource.Loading)

        try {
            val raw = execute(param)
            emit(handleResponse(raw))
        } catch (t: Throwable) {
            emit(Resource.Error(t.toUiError()))
        }

    }.flowOn(dispatcher)

    protected abstract suspend fun execute(param: P): NetworkResponse

    protected open fun handleResponse(raw: NetworkResponse): Resource<T> {

        val baseResponse = runCatching {
            json.decodeFromString(
                BaseResponse.serializer(serializer),
                raw.body
            )
        }.getOrNull()

        val backendMessage = baseResponse?.message?.takeIf { it.isNotBlank() }

        return when (raw.httpCode) {

            in 200..299 -> {
                baseResponse?.data?.let {
                    Resource.Success(it)
                } ?: Resource.Error(
                    UiError.Unknown(ErrorMessages.GENERIC_ERROR)
                )
            }

            400 -> Resource.Error(
                UiError.Validation(
                    backendMessage ?: ErrorMessages.INVALID_INPUT
                )
            )

            401 -> Resource.Error(
                UiError.Unauthorized(
                    backendMessage ?: ErrorMessages.SESSION_EXPIRED
                )
            )

            403 -> Resource.Error(
                UiError.Forbidden(
                    backendMessage ?: ErrorMessages.ACCESS_DENIED
                )
            )

            in 500..599 -> Resource.Error(
                UiError.Server(
                    backendMessage ?: ErrorMessages.SERVER_ERROR
                )
            )

            else -> Resource.Error(
                UiError.Unknown(
                    backendMessage ?: ErrorMessages.GENERIC_ERROR
                )
            )
        }
    }

}
