package com.mtv.app.core.provider.based

import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1

data class BaseUiState(
    val isLoading: Boolean = false,
    val errorDialog: DialogStateV1? = null
)
