package com.bluewhaleyt.common.color.extension

import androidx.core.graphics.ColorUtils

fun Number.toHexColor(): String {
    return String().format("#%08X", this.toInt())
}

fun Number.toHexColorWithQuotes(): String {
    return "\"${this.toHexColor()}\""
}

fun Number.hasLuminanceAbove(threshold: Float = 0.5f): Boolean {
    return ColorUtils.calculateLuminance(this.toInt()) > threshold
}

internal fun String.parseColor(pattern: Regex, convert: (String, String) -> String): String {
    return when {
        pattern.matches(this) -> {
            pattern.replace(this) { matchResult ->
                val color = matchResult.groupValues[1]
                val alpha = matchResult.groupValues[2]
                convert(color, alpha)
            }
        }
        else -> throw IllegalArgumentException("Invalid color string: $this")
    }
}

fun String.parseARGB(): String {
    return parseColor(Regex("#([0-9a-fA-F]{2})([0-9a-fA-F]{6})")) { color, alpha ->
        "#${color}${alpha}"
    }
}

fun String.parseRGBA(): String {
    return parseColor(Regex("#([0-9a-fA-F]{6})([0-9a-fA-F]{2})")) { color, alpha ->
        "#${alpha}${color}"
    }
}