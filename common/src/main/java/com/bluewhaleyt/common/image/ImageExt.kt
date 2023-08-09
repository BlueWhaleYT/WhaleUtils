package com.bluewhaleyt.common.image

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun Int.toDrawable(context: Context): Drawable? {
    return ContextCompat.getDrawable(context, this)
}