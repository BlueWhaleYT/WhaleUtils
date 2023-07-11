package com.bluewhaleyt.compose_common.color.extension

import androidx.compose.ui.graphics.Color

/**
 * Convert color to compose color
 *
 * @see [Color]
 * @return compose color of given color
 *
 * #### Example
 * ```kt
 * @Composable
 * fun App() {
 *     Text(
 *         text = "Hello World!",
 *         color = 0xFFFF0000.toComposeColor()
 *     )
 * }
 * ```
 */
fun Long.toComposeColor(): Color {
    return Color(this)
}