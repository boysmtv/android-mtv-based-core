package com.mtv.based.core.usecase

import com.mtv.based.core.NameResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.endpoint.ApiEndPoint
import com.mtv.based.core.network.usecase.BaseUseCase
import com.mtv.based.core.network.utils.NetworkRepository
import com.mtv.based.core.network.utils.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

//class GetUsersUseCase @Inject constructor(
//    private val repository: NetworkRepository,
//    @IoDispatcher dispatcher: CoroutineDispatcher
//) : BaseUseCase<Unit, NameResponse>(dispatcher, NameResponse::class) {
//
//    override suspend fun execute(param: Unit): NetworkResponse {
//        return repository.get(ApiEndPoint.getUser)
//    }
//
//}

class GetUsersUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, NameResponse>(dispatcher, NameResponse::class) {

    override suspend fun execute(param: Unit): NetworkResponse {
        return repository.get(ApiEndPoint.GetUsers)
    }

}