package com.mtv.based.core.usecase

import com.mtv.based.core.model.UserDto
import com.mtv.based.core.model.mapper.toFirebaseMap
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.firebase.config.FirebaseConfig
import com.mtv.based.core.network.firebase.datasource.FirebaseDataSource
import com.mtv.based.core.network.firebase.result.FirebaseResult
import com.mtv.based.core.network.firebase.usecase.BaseFirebaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserFirebaseUseCase @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<UserDto, Unit>(dispatcher) {

    override fun execute(param: UserDto): Flow<FirebaseResult<Unit>> {
        return dataSource.setDocument(
            collection = config.defaultCollection,
            documentId = param.id,
            data = param.toFirebaseMap()
        )
    }
}
