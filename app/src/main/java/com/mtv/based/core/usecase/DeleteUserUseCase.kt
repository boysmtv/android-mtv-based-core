/*
 * Project: Boys.mtv@gmail.com
 * File: DeleteUserUseCase.kt
 *
 * Last modified by Dedy Wijaya on 04/01/2026 21.14
 */

package com.mtv.based.core.usecase

import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.endpoint.ApiEndPoint
import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.based.core.network.repository.NetworkRepository
import com.mtv.based.core.network.model.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<String, Unit>(dispatcher, Unit::class) {

    override suspend fun execute(param: String): NetworkResponse {
        return repository.request(
            endpoint = ApiEndPoint.DeleteUser(id = param)
        )
    }
}
