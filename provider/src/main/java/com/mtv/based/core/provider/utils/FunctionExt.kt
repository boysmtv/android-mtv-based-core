package com.mtv.based.core.provider.utils

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.UiError
import com.mtv.based.core.network.utils.UiException
import com.mtv.based.core.network.utils.toUiError
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import kotlin.collections.get
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

@Suppress("UNCHECKED_CAST")
fun <T : Any> T.toMap(): Map<String, Any> {
    return this::class.memberProperties.associate { prop ->
        val kProp = prop as KProperty1<T, *>
        val value = kProp.get(this) ?: ""
        prop.name to value
    }
}

fun <T : Any> Map<String, Any?>.safeToDataClass(clazz: Class<T>): T {
    val kClass = clazz.kotlin
    val constructor = kClass.primaryConstructor!!

    val args = constructor.parameters.associateWith { param ->
        this[param.name]
    }

    return constructor.callBy(args)
}

suspend fun <T> safeApiCall(
    call: suspend () -> T
): Resource<T> {
    return try {
        Resource.Success(call())
    }
    catch (e: UiException) {
        Resource.Error(e.toUiError())
    }
    catch (e: HttpException) {
        when (e.code()) {
            401 -> Resource.Error(UiError.Unauthorized())
            403 -> Resource.Error(UiError.Forbidden())
            in 500..599 -> Resource.Error(UiError.Server())
            else -> Resource.Error(UiError.Unknown(e.message()))
        }
    }
    catch (e: ClientRequestException) {
        Resource.Error(UiError.Validation(e.message ?: ErrorMessages.GENERIC_ERROR))
    }
    catch (e: ServerResponseException) {
        Resource.Error(UiError.Server(e.message ?: ErrorMessages.GENERIC_ERROR))
    }
    catch (e: ResponseException) {
        Resource.Error(UiError.Unknown(e.message ?: ErrorMessages.GENERIC_ERROR))
    }
    catch (e: IOException) {
        Resource.Error(UiError.Network())
    }
    catch (e: Exception) {
        Resource.Error(UiError.Unknown(e.message ?: ErrorMessages.GENERIC_ERROR))
    }
}

suspend fun <T> safeFirebaseCall(
    call: suspend () -> T
): Resource<T> {
    return try {
        Resource.Success(call())
    } catch (e: FirebaseNetworkException) {
        Resource.Error(UiError.Network(e.message ?: ErrorMessages.GENERIC_ERROR))
    } catch (e: FirebaseAuthException) {
        Resource.Error(UiError.Unauthorized(e.message ?: ErrorMessages.GENERIC_ERROR))
    } catch (e: FirebaseException) {
        Resource.Error(UiError.Unknown(e.message ?: ErrorMessages.GENERIC_ERROR))
    } catch (e: Exception) {
        Resource.Error(UiError.Unknown(e.message ?: ErrorMessages.GENERIC_ERROR))
    } catch (e: UiException) {
        Resource.Error(UiError.Validation(e.message ?: ErrorMessages.GENERIC_ERROR))
    }
}

fun <T> flowResource(
    block: suspend () -> Resource<T>
): Flow<Resource<T>> = flow {
    emit(Resource.Loading)
    emit(block())
}