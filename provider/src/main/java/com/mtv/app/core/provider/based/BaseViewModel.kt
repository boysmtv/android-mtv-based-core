package com.mtv.app.core.provider.based

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.UiErrorFirebase
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.UiError
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.ErrorDialogStateV1
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _baseUiState = MutableStateFlow(BaseUiState())
    val baseUiState: StateFlow<BaseUiState> = _baseUiState

    /* ============================================================
     * NETWORK USE CASE
     * ============================================================ */
    protected fun <T> launchUseCase(
        target: MutableStateFlow<Resource<T>>,
        block: suspend () -> Flow<Resource<T>>
    ) {
        viewModelScope.launch {
            block().collect { result ->
                target.value = result

                _baseUiState.update {
                    it.copy(isLoading = result is Resource.Loading)
                }

                if (result is Resource.Error) {
                    showError(result.error)
                }
            }
        }
    }

    protected fun <T> launchFirebaseUseCase(
        target: MutableStateFlow<ResourceFirebase<T>>,
        block: suspend () -> Flow<ResourceFirebase<T>>,
        loading: Boolean = true,
    ) {
        viewModelScope.launch {
            block().collect { result ->
                target.value = result

                if (loading) {
                    _baseUiState.update {
                        it.copy(isLoading = result is ResourceFirebase.Loading)
                    }
                }

                if (result is ResourceFirebase.Error) {
                    showFirebaseError(result.error)
                }
            }
        }
    }

    fun <S, T> BaseViewModel.collectFieldSuccess(
        parent: StateFlow<S>,
        selector: (S) -> ResourceFirebase<T>,
        onSuccess: suspend (T) -> Unit
    ) {
        viewModelScope.launch {
            parent.collect { state ->
                val field = selector(state)
                if (field is ResourceFirebase.Success) {
                    onSuccess(field.data)
                }
            }
        }
    }


    /* ============================================================
     * ERROR HANDLER
     * ============================================================ */
    private fun showError(error: UiError) {
        val message = error.message
            .takeIf { it.isNotBlank() }
            ?: ErrorMessages.GENERIC_ERROR

        _baseUiState.update {
            it.copy(
                errorDialog = ErrorDialogStateV1(
                    message = message
                )
            )
        }
    }

    private fun showFirebaseError(error: UiErrorFirebase) {
        val message = error.message
            .takeIf { it.isNotBlank() }
            ?: ErrorMessages.GENERIC_ERROR

        _baseUiState.update {
            it.copy(
                errorDialog = ErrorDialogStateV1(
                    message = message
                )
            )
        }
    }

    fun dismissError() {
        _baseUiState.update {
            it.copy(errorDialog = null)
        }
    }
}
