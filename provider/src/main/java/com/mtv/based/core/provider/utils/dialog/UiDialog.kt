/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: UiDialog.kt
 *
 * Last modified by Dedy Wijaya on 25/03/26 11.38
 */

package com.mtv.based.core.provider.utils.dialog

import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1

sealed class UiDialog {

    data class Center(
        val state: DialogStateV1,
        val onPrimary: (() -> Unit)? = null,
        val onSecondary: (() -> Unit)? = null,
        val onDismiss: (() -> Unit)? = null
    ) : UiDialog()

}