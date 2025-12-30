package com.mtv.app.core.provider.based

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.ErrorDialogStateV1
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _baseUiState = MutableStateFlow(BaseUiState())
    val baseUiState: StateFlow<BaseUiState> = _baseUiState

    protected fun <T> launchUseCase(
        target: MutableStateFlow<Resource<T>>,
        block: suspend () -> Flow<Resource<T>>
    ) {
        viewModelScope.launch {
            block().collect { result ->
                target.value = result

                _baseUiState.update { state ->
                    state.copy(isLoading = result is Resource.Loading)
                }

                if (result is Resource.Error) {
                    showError(
                        ErrorDialogStateV1(
                            title = "Warning",
                            message = result.error.message
                        )
                    )
                }
            }
        }
    }

    protected fun showError(error: ErrorDialogStateV1) {
        _baseUiState.update { state ->
            state.copy(errorDialog = error)
        }
    }

    fun dismissError() {
        _baseUiState.update { state ->
            state.copy(errorDialog = null)
        }
    }
}
