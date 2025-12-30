package com.mtv.based.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.based.core.model.CreateUserRequest
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.usecase.CreateUserUseCase
import com.mtv.based.core.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModels @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _users = MutableStateFlow<Resource<NameResponse>>(Resource.Loading)
    val users: StateFlow<Resource<NameResponse>> = _users

    private val _createUserResponse = MutableStateFlow<Resource<NameResponse>>(Resource.Loading)
    val createUserResponse: StateFlow<Resource<NameResponse>> = _createUserResponse

    fun fetchUsers() {
        viewModelScope.launch {
            getUsersUseCase(Unit).collect {
                _users.value = it
            }
        }
    }

    fun createUser(name: String, email: String, age: Int) {
        viewModelScope.launch {
            createUserUseCase(CreateUserRequest(name, email, age)).collect {
                _createUserResponse.value = it
            }
        }
    }
}

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val createUserUseCase: CreateUserUseCase
) : BaseViewModel() {

    val users = MutableStateFlow<Resource<NameResponse>>(Resource.Loading)
    val createUserResponse = MutableStateFlow<Resource<NameResponse>>(Resource.Loading)

    fun fetchUsers() {
        launchUseCase(users) {
            getUsersUseCase(Unit)
        }
    }

    fun createUser(name: String, email: String, age: Int) {
        launchUseCase(createUserResponse) {
            createUserUseCase(CreateUserRequest(name, email, age))
        }
    }

}
