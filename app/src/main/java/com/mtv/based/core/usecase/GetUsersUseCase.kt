package com.mtv.based.core.usecase

import com.mtv.based.core.NameResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.endpoint.ApiEndPoint
import com.mtv.based.core.network.usecase.BaseUseCase
import com.mtv.based.core.network.repository.NetworkRepository
import com.mtv.based.core.network.model.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, NameResponse>(dispatcher, NameResponse::class) {

    override suspend fun execute(param: Unit): NetworkResponse {
        return repository.request(endpoint = ApiEndPoint.GetUsers)
    }

}