package com.mtv.app.core.provider.utils.device

import kotlinx.serialization.Serializable

@Serializable
data class DeviceInfo(
    val installationId: String?,
    val androidId: String?,

    val brand: String?,
    val manufacturer: String?,
    val model: String?,
    val device: String?,
    val hardware: String?,
    val supportedAbis: String?,
    val sdkVersion: Int,
    val androidVersion: String?,

    val screenWidth: Int,
    val screenHeight: Int,
    val densityDpi: Int?,

    val locale: String?,
    val timezone: String?,
    val batteryLevel: Int?,
    val networkType: String?,

    val freeStorageMb: Long,
    val freeRamMb: Long,

    val appVersionName: String?,
    val appVersionCode: Long,
    val firstInstallTime: Long,
    val lastUpdateTime: Long
)
