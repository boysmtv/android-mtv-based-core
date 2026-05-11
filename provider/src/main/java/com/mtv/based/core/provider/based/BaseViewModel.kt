/*
 * Project: App Core Compose
 * Author: Boys.mtv@gmail.com
 * File: BaseViewModel.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.53
 */

package com.mtv.based.core.provider.based

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtv.based.core.network.utils.LoadState
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.UiError
import com.mtv.based.core.provider.utils.dialog.UiDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _baseUiState = MutableStateFlow(BaseUiState())
    val baseUiState: StateFlow<BaseUiState> = _baseUiState

    private var loadingCount = 0

    private var job: Job? = null

    protected fun <T> observeDataFlow(
        flow: Flow<Resource<T>>,
        onLoad: (() -> Unit)? = null,
        onError: ((UiError) -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
        onState: ((LoadState<T>) -> Unit)? = null
    ) {
        job?.cancel()
        job = viewModelScope.launch {
            flow.collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        onLoad?.invoke()
                        onState?.invoke(LoadState.Loading)
                    }

                    is Resource.Success -> {
                        onSuccess?.invoke(result.data)
                        onState?.invoke(LoadState.Success(result.data))
                    }

                    is Resource.Error -> {
                        onError?.invoke(result.error)
                        onState?.invoke(LoadState.Error(result.error))
                    }

                    else -> Unit
                }
            }
        }
    }

    fun showLoading() {
        loadingCount++
        updateLoading()
    }

    fun hideLoading() {
        loadingCount = (loadingCount - 1).coerceAtLeast(0)
        updateLoading()
    }

    private fun updateLoading() {
        _baseUiState.update {
            it.copy(isLoading = loadingCount > 0)
        }
    }

    fun setDialog(dialog: UiDialog) {
        _baseUiState.update {
            it.copy(dialog = dialog)
        }
    }

    fun dismissDialog() {
        _baseUiState.update {
            it.copy(dialog = null)
        }
    }

}
