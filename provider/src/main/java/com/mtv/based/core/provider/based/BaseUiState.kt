package com.mtv.based.core.provider.based

import com.mtv.based.core.provider.utils.dialog.UiDialog

data class BaseUiState(
    val isLoading: Boolean = false,
    val dialog: UiDialog? = null
)
