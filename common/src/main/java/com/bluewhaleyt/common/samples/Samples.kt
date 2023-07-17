package com.bluewhaleyt.common.samples

import java.util.Locale

private class Samples {
    private fun sample() {
        val number = 32.14159
        val str = String.format(Locale.US, "%.2f", number)
        println(str)
    }
}
