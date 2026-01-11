package com.mtv.based.core.usecase

import com.mtv.based.core.datasource.checkFields
import com.mtv.based.core.model.UserDto
import com.mtv.based.core.model.mapper.toFirebaseMap
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.firebase.config.FirebaseConfig
import com.mtv.based.core.network.firebase.datasource.FirebaseDataSource
import com.mtv.based.core.network.firebase.result.FirebaseResult
import com.mtv.based.core.network.firebase.usecase.BaseFirebaseUseCase
import com.mtv.based.core.network.firebase.utils.FirebaseUiError
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

    override fun execute(param: UserDto): Flow<FirebaseResult<Unit>> = flow {
        emit(FirebaseResult.Loading)

        val exists = dataSource.isExistByFields(
            collection = config.defaultCollection,
            data = param.checkFields()
        ).first()

        if (exists) {
            emit(FirebaseResult.Error(FirebaseUiError.Permission(ErrorMessages.USER_ALREADY_EXISTS)))
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
