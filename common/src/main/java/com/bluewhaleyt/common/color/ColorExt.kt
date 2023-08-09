package com.bluewhaleyt.common.color

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap

fun Drawable.getDominantColor(): Int {
    val bitmap: Bitmap = this.toBitmap()
    val width = bitmap.width
    val height = bitmap.height
    val size = width * height
    val pixels = IntArray(size)
    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
    var color: Int
    var r = 0
    var g = 0
    var b = 0
    var a: Int
    var count = 0
    for (i in pixels.indices) {
        color = pixels[i]
        a = Color.alpha(color)
        if (a > 0) {
            r += Color.red(color)
            g += Color.green(color)
            b += Color.blue(color)
            count++
        }
    }
    r /= count
    g /= count
    b /= count
    r = r shl 16 and 0x00FF0000
    g = g shl 8 and 0x0000FF00
    b = b and 0x000000FF
    color = -0x1000000 or r or g or b
    return color
}