package com.mtv.app.core.provider.utils

class Constants {

    companion object {

        object Session {
            const val SESSION_UID = "uid"
            const val SESSION_IS_LOGGED_IN = "isLoggedIn"
        }

        object DeviceInfoKeys {

            // Device identifiers
            const val INSTALLATION_PREF = "installation_prefs"
            const val INSTALLATION_ID = "installation_id"
            const val ANDROID_ID = "android_id"
            const val DEVICE_ID = "device_id"

            // Hardware info
            const val DEVICE_MODEL = "device_model"
            const val DEVICE_MANUFACTURER = "device_manufacturer"
            const val DEVICE_BRAND = "device_brand"
            const val DEVICE_PRODUCT = "device_product"
            const val DEVICE_BOARD = "device_board"
            const val DEVICE_BOOTLOADER = "device_bootloader"
            const val DEVICE_HARDWARE = "device_hardware"
            const val DEVICE_FINGERPRINT = "device_fingerprint"

            // OS info
            const val OS_VERSION = "os_version"
            const val SDK_INT = "sdk_int"
            const val SECURITY_PATCH = "security_patch"

            // App info
            const val APP_VERSION_NAME = "app_version_name"
            const val APP_VERSION_CODE = "app_version_code"
            const val APP_PACKAGE_NAME = "app_package_name"
            const val APP_INSTALLER = "app_installer"

            // Network info
            const val NETWORK_TYPE = "network_type"
            const val WIFI_SSID = "wifi_ssid"
            const val WIFI_BSSID = "wifi_bssid"
            const val IP_ADDRESS = "ip_address"
            const val MAC_ADDRESS = "mac_address"

            // Display info
            const val SCREEN_WIDTH = "screen_width"
            const val SCREEN_HEIGHT = "screen_height"
            const val SCREEN_DENSITY = "screen_density"
            const val SCREEN_DPI = "screen_dpi"

            // Battery info
            const val BATTERY_LEVEL = "battery_level"
            const val BATTERY_STATUS = "battery_status"
            const val BATTERY_HEALTH = "battery_health"
            const val IS_CHARGING = "is_charging"

            // Storage info
            const val STORAGE_TOTAL = "storage_total"
            const val STORAGE_AVAILABLE = "storage_available"

            // Locale info
            const val LOCALE_LANGUAGE = "locale_language"
            const val LOCALE_COUNTRY = "locale_country"
            const val TIMEZONE = "timezone"

            // Other device states
            const val IS_ROOTED = "is_rooted"
            const val IS_EMULATOR = "is_emulator"
            const val DEVICE_NAME = "device_name"

            // JSON storage
            const val DEVICE_INFO_JSON = "device_info_json"
        }
    }
}