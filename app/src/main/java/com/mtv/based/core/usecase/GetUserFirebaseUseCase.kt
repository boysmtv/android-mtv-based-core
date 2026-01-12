package com.mtv.based.core.usecase

import com.mtv.based.core.model.UserDto
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserFirebaseUseCase @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<String, UserDto>(dispatcher) {

    override fun execute(param: String): Flow<ResourceFirebase<UserDto>> {
        return dataSource.getDocument(
            collection = config.defaultCollection,
            documentId = param
        ) { data ->
            UserDto(
                id = param,
                name = data["name"] as String,
                email = data["email"] as String
            )
        }
    }
}
