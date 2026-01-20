package com.mtv.app.core.provider.utils.device

import com.mtv.app.core.provider.utils.Constants.Companion.DeviceInfoKeys.INSTALLATION_ID
import com.mtv.app.core.provider.utils.SecurePrefs
import java.util.UUID
import javax.inject.Inject

class InstallationIdProvider @Inject constructor(
    private val prefs: SecurePrefs
) {

    fun getInstallationId(): String {
        var id = prefs.getString(INSTALLATION_ID, null)

        if (id == null) {
            id = UUID.randomUUID().toString()
            prefs.putString(INSTALLATION_ID, id)
        }
        return id
    }

}
