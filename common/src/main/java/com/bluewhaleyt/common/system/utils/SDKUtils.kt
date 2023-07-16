package com.bluewhaleyt.common.system.utils

import android.annotation.SuppressLint
import android.os.Build

import androidx.annotation.ChecksSdkIntAtLeast

object SDKUtils {
    @SuppressLint("AnnotateVersionCheck")
    private val sdkInt = Build.VERSION.SDK_INT

    /**
     * Returns whether the device's SDK version is at least `21` (Android Lollipop).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.LOLLIPOP
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP)
    val isAtLeastSDK21: Boolean
        get() = sdkInt >= Build.VERSION_CODES.LOLLIPOP

    /**
     * Returns whether the device's SDK version is at least `22` (Android Lollipop MR1).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.LOLLIPOP_MR1
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    val isAtLeastSDK22: Boolean
        get() = sdkInt >= Build.VERSION_CODES.LOLLIPOP_MR1

    /**
     * Returns whether the device's SDK version is at least `23` (Android Marshmallow).
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.M
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
    val isAtLeastSDK23: Boolean
        get() = sdkInt >= Build.VERSION_CODES.M

    /**
     * Returns whether the device's SDK version is at least `24` (Android Nougat).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.N
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
    val isAtLeastSDK24: Boolean
        get() = sdkInt >= Build.VERSION_CODES.N

    /**
     * Returns whether the device's SDK version is at least `25` (Android Nougat MR1).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.N_MR1
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N_MR1)
    val isAtLeastSDK25: Boolean
        get() = sdkInt >= Build.VERSION_CODES.N_MR1

    /**
     * Returns whether the device's SDK version is at least `26` (Android Oreo).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.O
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    val isAtLeastSDK26: Boolean
        get() = sdkInt >= Build.VERSION_CODES.O

    /**
     * Returns whether the device's SDK version is at least `27` (Android Oreo MR1).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.O_MR1
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O_MR1)
    val isAtLeastSDK27: Boolean
        get() = sdkInt >= Build.VERSION_CODES.O_MR1

    /**
     * Returns whether the device's SDK version is at least `28` (Android Pie).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.P
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    val isAtLeastSDK28: Boolean
        get() = sdkInt >= Build.VERSION_CODES.P

    /**
     * Returns whether the device's SDK version is at least `29` (Android 10).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.Q
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    val isAtLeastSDK29: Boolean
        get() = sdkInt >= Build.VERSION_CODES.Q

    /**
     * Returns whether the device's SDK version is at least `30` (Android 11).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.R
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
    val isAtLeastSDK30: Boolean
        get() = sdkInt >= Build.VERSION_CODES.R

    /**
     * Returns whether the device's SDK version is at least `31` (Android 12).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.S
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    val isAtLeastSDK31: Boolean
        get() = sdkInt >= Build.VERSION_CODES.S

    /**
     * Returns whether the device's SDK version is at least `32` (Android 12.1 / Android 12L).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.S_V2
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S_V2)
    val isAtLeastSDK32: Boolean
        get() = sdkInt >= Build.VERSION_CODES.S_V2

    /**
     * Returns whether the device's SDK version is at least `33` (Android 13 - Tiramisu).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.TIRAMISU
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    val isAtLeastSDK33: Boolean
        get() = sdkInt >= Build.VERSION_CODES.TIRAMISU

    /**
     * Returns whether the device's SDK version is at least `34` (Android 14 - Upside Down Cake).
     *
     * @see Build.VERSION.SDK_INT
     * @see Build.VERSION_CODES.UPSIDE_DOWN_CAKE
     */
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    val isAtLeastSDK34: Boolean
        get() = sdkInt >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
}