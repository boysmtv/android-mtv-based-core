package com.mtv.app.core.provider.utils.device

import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import androidx.window.layout.WindowMetricsCalculator
import java.io.File
import java.util.Locale
import java.util.TimeZone

class DeviceInfoProvider(
    private val context: Context,
    private val installationIdProvider: InstallationIdProvider
) {

    fun getAllDeviceInfo(): DeviceInfo {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
        val bounds = metrics.bounds

        val battery = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

        val memoryInfo = ActivityManager.MemoryInfo()
        am.getMemoryInfo(memoryInfo)

        val density = context.resources.displayMetrics.densityDpi

        return DeviceInfo(
            installationId = installationIdProvider.getInstallationId(),
            androidId = Settings.Secure.getString(
                context.contentResolver, Settings.Secure.ANDROID_ID
            ),

            brand = Build.BRAND,
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            device = Build.DEVICE,
            hardware = Build.HARDWARE,
            supportedAbis = Build.SUPPORTED_ABIS.joinToString(),
            sdkVersion = Build.VERSION.SDK_INT,
            androidVersion = Build.VERSION.RELEASE,

            screenWidth = bounds.width(),
            screenHeight = bounds.height(),
            densityDpi = density,

            locale = Locale.getDefault().toString(),
            timezone = TimeZone.getDefault().id,
            batteryLevel = battery.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY),
            networkType = getNetworkType(cm),

            freeStorageMb = getFreeStorage(),
            freeRamMb = memoryInfo.availMem / 1024 / 1024,

            appVersionName = packageInfo.versionName,
            appVersionCode = packageInfo.longVersionCode,
            firstInstallTime = packageInfo.firstInstallTime,
            lastUpdateTime = packageInfo.lastUpdateTime
        )
    }


    private fun getNetworkType(cm: ConnectivityManager): String {
        val network = cm.activeNetwork ?: return "none"
        val caps = cm.getNetworkCapabilities(network) ?: return "none"

        return when {
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "wifi"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "cellular"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "ethernet"
            else -> "unknown"
        }
    }

    private fun getFreeStorage(): Long {
        val file = File(context.filesDir.path)
        return file.freeSpace / 1024 / 1024
    }
}