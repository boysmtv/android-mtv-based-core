package com.mtv.based.core.usecase

import com.mtv.based.core.datasource.checkFields
import com.mtv.based.core.model.UserDto
import com.mtv.based.core.model.mapper.toFirebaseMap
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.based.core.network.utils.UiErrorFirebase
import com.mtv.based.core.network.utils.ErrorMessages
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveUserFirebaseUseCase @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<UserDto, Unit>(dispatcher) {

    override fun execute(param: UserDto): Flow<ResourceFirebase<Unit>> = flow {
        emit(ResourceFirebase.Loading)

        val exists = dataSource.isExistByFields(
            collection = config.defaultCollection,
            data = param.checkFields()
        ).first()

        if (exists) {
            emit(ResourceFirebase.Error(UiErrorFirebase.Permission(ErrorMessages.USER_ALREADY_EXISTS)))
            return@flow
        }

        dataSource.setDocument(
            collection = config.defaultCollection,
            documentId = param.id,
            data = param.toFirebaseMap()
        ).collect { result ->
            emit(result)
        }
    }
}
