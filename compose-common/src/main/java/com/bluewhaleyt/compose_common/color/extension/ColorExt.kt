package com.bluewhaleyt.compose_common.color.extension

import androidx.compose.ui.graphics.Color

fun Long.toComposeColor(): Color {
    return Color(this)
}