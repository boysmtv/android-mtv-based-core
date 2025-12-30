package com.mtv.app.core.provider.based

import com.mtv.based.uicomponent.core.component.dialog.dialogv1.ErrorDialogStateV1

data class BaseUiState(
    val isLoading: Boolean = false,
    val errorDialog: ErrorDialogStateV1? = null
)
