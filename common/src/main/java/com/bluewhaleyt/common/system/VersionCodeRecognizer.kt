package com.bluewhaleyt.common.system

import android.os.Build

internal object VersionCodeRecognizer {
    fun getVersionCodeName(sdkVersion: Int): String {
        val s: String
        when (sdkVersion) {
            1 -> s = Build.VERSION_CODES::BASE.name // Android 1.0
            2 -> s = Build.VERSION_CODES::BASE_1_1.name // Android 1.1
            3 -> s = Build.VERSION_CODES::CUPCAKE.name // Android 1.5
            4 -> s = Build.VERSION_CODES::DONUT.name // Android 1.6
            5 -> s = Build.VERSION_CODES::ECLAIR.name // Android 2.0
            6 -> s = Build.VERSION_CODES::ECLAIR_0_1.name // Android 2.0.1
            7 -> s = Build.VERSION_CODES::ECLAIR_MR1.name // Android 2.1
            8 -> s = Build.VERSION_CODES::FROYO.name // Android 2.2
            9 -> s = Build.VERSION_CODES::GINGERBREAD.name // Android 2.3
            10 -> s = Build.VERSION_CODES::GINGERBREAD_MR1.name // Android 2.3.3
            11 -> s = Build.VERSION_CODES::HONEYCOMB.name // Android 3.0
            12 -> s = Build.VERSION_CODES::HONEYCOMB_MR1.name // Android 3.1
            13 -> s = Build.VERSION_CODES::HONEYCOMB_MR2.name // Android 3.2
            14 -> s = Build.VERSION_CODES::ICE_CREAM_SANDWICH.name // Android 4.0
            15 -> s = Build.VERSION_CODES::ICE_CREAM_SANDWICH_MR1.name // Android 4.03
            16 -> s = Build.VERSION_CODES::JELLY_BEAN.name // Android 4.1
            17 -> s = Build.VERSION_CODES::JELLY_BEAN_MR1.name // Android 4.2
            18 -> s = Build.VERSION_CODES::JELLY_BEAN_MR2.name // Android 4.3
            19 -> s = Build.VERSION_CODES::KITKAT.name // Android 4.4
            20 -> s = Build.VERSION_CODES::KITKAT_WATCH.name // Android 4.4W
            21 -> s = Build.VERSION_CODES::LOLLIPOP.name // Android 5.0
            22 -> s = Build.VERSION_CODES::LOLLIPOP_MR1.name // Android 5.1
            23 -> s = Build.VERSION_CODES::M.name // Android 6.0
            24 -> s = Build.VERSION_CODES::N.name // Android 7.0
            25 -> s = Build.VERSION_CODES::N_MR1.name // Android 7.1
            26 -> s = Build.VERSION_CODES::O.name // Android 8.0
            27 -> s = Build.VERSION_CODES::O_MR1.name // Android 8.1
            28 -> s = Build.VERSION_CODES::P.name // Android 9
            29 -> s = Build.VERSION_CODES::Q.name // Android 10
            30 -> s = Build.VERSION_CODES::R.name // Android 11
            31 -> s = Build.VERSION_CODES::S.name // Android 12
            32 -> s = Build.VERSION_CODES::S_V2.name // Android 12L
            33 -> s = Build.VERSION_CODES::TIRAMISU.name // Android 13
            34 -> s = Build.VERSION_CODES::UPSIDE_DOWN_CAKE.name // Android 14
            else -> s = "UNKNOWN"
        }
        return s.uppercase()
    }
}