package com.mtv.based.core.config

import com.mtv.based.core.BuildConfig
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.config.FirebaseConfigProvider

class AppFirebaseConfigProvider : FirebaseConfigProvider {

    override fun provide(): FirebaseConfig =
        FirebaseConfig(
            projectId = BuildConfig.FIREBASE_PROJECT_ID,
            defaultCollection = BuildConfig.FIREBASE_DEFAULT_CONNECTION
        )

}