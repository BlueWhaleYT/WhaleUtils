package com.bluewhaleyt.common.color.extension

import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

/**
 * Convert [Long] color to hex color e.g. `#ff0000`
 *
 * #### Example
 * ```kt
 * // Kotlin
 * val hex = Color.RED.toLong().toHexColor()
 * ```
 *
 * ```java
 * // Java
 * String hex = ColorExtKt.toHexColor(Color.RED);
 * ```
 *
 * @return hex color of given color
 */
fun Long.toHexColor(): String {
    return String().format("#%08X", this.toInt())
}

/**
 * Convert [Long] color to hex color with quotes i.e `"` e.g. `"#ff0000"`
 *
 * #### Example
 * ```kt
 * // Kotlin
 * val hex = Color.RED.toLong().toHexColorWithQuotes()
 * ```
 *
 * ```java
 * // Java
 * String hex = ColorExtKt.toHexColorWithQuotes(Color.RED);
 * ```
 *
 * @return hex color with quotes of given color
 */
fun Long.toHexColorWithQuotes(): String {
    return "\"${this.toHexColor()}\""
}

/**
 * Determines if the given [ColorInt] color has a luminance value above a specified threshold.
 *
 * #### Example
 * ```kt
 * // Kotlin
 * val textColor = if (color.hasLuminanceAbove()) Color.BLACK else Color.WHITE
 * ```
 *
 * ```java
 * // Java
 * int textColor = ColorExt.hasLuminanceAbove(color, 0.5f) ? Color.BLACK : Color.WHITE;
 * ```
 *
 * @param threshold The threshold luminance value to compare against. Default value is `0.5f`.
 * @return `true` if the color has a luminance value above the threshold, `false` otherwise.
 * @see ColorUtils.calculateLuminance
 * @receiver [ColorInt]
 */
fun @receiver:ColorInt Int.hasLuminanceAbove(threshold: Float = 0.5f): Boolean {
    return ColorUtils.calculateLuminance(this) > threshold
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