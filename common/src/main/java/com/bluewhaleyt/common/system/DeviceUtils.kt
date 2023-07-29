package com.bluewhaleyt.common.system

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

class DeviceUtils {

    /**
     * The end-user-visible name for the end product.
     *
     * @see Build.MODEL
     */
    val model = Build.MODEL

    /**
     * The name of the overall product.
     *
     * @see Build.PRODUCT
     */
    val product = Build.PRODUCT

    /**
     * The manufacturer of the product or hardware.
     *
     * @see Build.MANUFACTURER
     */
    val manufacturer = Build.MANUFACTURER

    /**
     * A string that uniquely identifies this build. Do not attempt to parse this value.
     *
     * @see Build.DISPLAY
     */
    val buildNumber = Build.DISPLAY

    /**
     * A string that uniquely identifies this build. Do not attempt to parse this value.
     *
     * @see Build.FINGERPRINT
     */
    val buildFingerprint = Build.FINGERPRINT

    /**
     * The system bootloader version number.
     *
     * @see Build.BOOTLOADER
     */
    val bootLoader = Build.BOOTLOADER

    /**
     * The SDK version of the software currently running on this hardware device.
     * This value never changes while a device is booted, but it may increase when the hardware manufacturer provides an OTA update.
     *
     * @see Build.VERSION.SDK_INT
     * @see VersionCodeRecognizer.getVersionCode
     */
    @ChecksSdkIntAtLeast(extension = 0) val sdkVersion = Build.VERSION.SDK_INT

    val sdkVersionCodeName = VersionCodeRecognizer.getVersionCodeName(sdkVersion)

    /**
     * The user-visible version string.
     *
     * @see Build.VERSION.RELEASE
     */
    val osVersion = Build.VERSION.RELEASE

    /**
     * The version string for the radio firmware.
     *
     * @see Build.getRadioVersion
     */
    val baseHandVersion = Build.getRadioVersion()

    /**
     * Returns the version of the currently-running JVM.
     *
     * @see System.getProperty
     */
    val jvmVersion = System.getProperty("java.vm.version")

    /**
     *  Returns the name of the currently-running JVM.
     *
     *  @see System.getProperty
     */
    val jvmName = System.getProperty("java.vm.name")

    /**
     * Returns the name of the Java Runtime Environment vendor.
     *
     * @see System.getProperty
     */
    val runtimeName = System.getProperty("java.runtime.name")

    /**
     * Checks if the JVM in use is Dalvik.
     *
     * @see runtimeName
     */
    val isUsingDalvikJVM = runtimeName?.lowercase()?.contains("dalvik") == true

    /**
     * Checks if the JVM in use is ART.
     *
     * @see runtimeName
     */
    val isUsingARTJVM = runtimeName?.lowercase()?.contains("art") == true
}