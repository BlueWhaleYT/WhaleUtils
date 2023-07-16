package com.bluewhaleyt.common.system.utils

import android.content.Context
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import com.bluewhaleyt.common.system.VersionCodeRecognizer

class DeviceUtils(
    private val context: Context
) {
    val model = Build.MODEL
    val product = Build.PRODUCT
    val manufacturer = Build.MANUFACTURER
    val buildNumber = Build.DISPLAY
    val buildFingerprint = Build.FINGERPRINT
    val bootLoader = Build.BOOTLOADER

    @ChecksSdkIntAtLeast(extension = 0) val sdkVersion = Build.VERSION.SDK_INT
    val sdkVersionCode = VersionCodeRecognizer.getVersionCode(sdkVersion)
    val osVersion = Build.VERSION.RELEASE
    val baseHandVersion = Build.getRadioVersion()
    val jvmVersion = System.getProperty("java.vm.version")
    val jvmName = System.getProperty("java.vm.name")
    val runtimeName = System.getProperty("java.runtime.name")

    val isUsingDalvikRuntime = runtimeName?.lowercase()?.contains("dalvik") == true
    val isUsingARTRuntime = runtimeName?.lowercase()?.contains("art") == true
}