package com.mtv.based.core

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.based.core.model.LoginRequest
import com.mtv.based.core.model.LoginResponse
import com.mtv.based.core.model.UserDto
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.usecase.GetUserFirebaseUseCase
import com.mtv.based.core.usecase.GetUsersUseCase
import com.mtv.based.core.usecase.LoginUseCase
import com.mtv.based.core.usecase.SaveUserFirebaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val loginUseCase: LoginUseCase,
    private val getUserFirebaseUseCase: GetUserFirebaseUseCase,
    private val saveUserFirebaseUseCase: SaveUserFirebaseUseCase
) : BaseViewModel() {

    val getUser = MutableStateFlow<Resource<NameResponse>>(Resource.Loading)
    val createUser = MutableStateFlow<Resource<NameResponse>>(Resource.Loading)
    val postLogin = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading)
    val userFirebase = MutableStateFlow<ResourceFirebase<UserDto>>(ResourceFirebase.Loading)
    val saveUserFirebase = MutableStateFlow<ResourceFirebase<Unit>>(ResourceFirebase.Loading)


    fun fetchUsers() = launchUseCase(getUser) {
        getUsersUseCase(Unit)
    }

    fun doLogin(loginRequest: LoginRequest) = launchUseCase(postLogin) {
        loginUseCase(loginRequest)
    }

    fun fetchUserFirebase(userId: String) {
        launchFirebaseUseCase(userFirebase) {
            getUserFirebaseUseCase(userId)
        }
    }

    fun saveUser(id: String, name: String, email: String) {
        launchFirebaseUseCase(saveUserFirebase) {
            saveUserFirebaseUseCase(UserDto(id, name, email))
        }
    }
}