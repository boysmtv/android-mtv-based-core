package com.mtv.based.core.usecase

import com.mtv.based.core.endpoint.ApiEndPoint
import com.mtv.based.core.model.LoginRequest
import com.mtv.based.core.model.LoginResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.repository.NetworkRepository
import com.mtv.app.core.provider.based.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<LoginRequest, LoginResponse>(dispatcher, LoginResponse::class) {

    override suspend fun execute(param: LoginRequest): NetworkResponse {
        return repository.request(
            endpoint = ApiEndPoint.AuthLogin,
            body = param
        )
    }

}
