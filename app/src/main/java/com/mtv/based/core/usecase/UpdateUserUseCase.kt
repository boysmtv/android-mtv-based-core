/*
 * Project: Boys.mtv@gmail.com
 * File: UpdateUserUseCase.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 21.13
 */

package com.mtv.based.core.usecase

import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.endpoint.ApiEndPoint
import com.mtv.based.core.network.usecase.BaseUseCase
import com.mtv.based.core.network.utils.NetworkRepository
import com.mtv.based.core.network.utils.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<String, Unit>(dispatcher, Unit::class) {

    override suspend fun execute(param: String): NetworkResponse {
        return repository.request(
            endpoint = ApiEndPoint.UpdateUser(param),
            body = param
        )
    }

}
