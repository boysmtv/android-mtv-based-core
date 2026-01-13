package com.mtv.based.core.usecase

import com.mtv.based.core.NameResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.endpoint.ApiEndPoint
import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.based.core.model.LoginRequest
import com.mtv.based.core.model.LoginResponse
import com.mtv.based.core.network.repository.NetworkRepository
import com.mtv.based.core.network.model.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, NameResponse>(dispatcher) {

    override suspend fun execute(param: Unit): NetworkResponse<NameResponse> {
        return repository.request(
            endpoint = ApiEndPoint.GetUsers,
            body = param,
            serializer = NameResponse.serializer()
        )
    }

}