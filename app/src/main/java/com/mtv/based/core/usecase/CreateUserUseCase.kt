package com.mtv.based.core.usecase


import com.mtv.based.core.NameResponse
import com.mtv.based.core.model.CreateUserRequest
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.endpoint.ApiEndPoint
import com.mtv.based.core.network.usecase.BaseUseCase
import com.mtv.based.core.network.utils.NetworkRepository
import com.mtv.based.core.network.utils.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<CreateUserRequest, NameResponse>(dispatcher, NameResponse::class) {

    override suspend fun execute(param: CreateUserRequest): NetworkResponse {
        return repository.post(
            endpoint = ApiEndPoint.postUser,
            body = param
        )
    }
}
