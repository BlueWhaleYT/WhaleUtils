package com.bluewhaleyt.sora_editor.textmate

import android.content.Context
import android.util.Log
import com.bluewhaleyt.common.common.getFileContentFromAssets

class TextMateColorSchemeBuilder(
    private val context: Context
) {
    var backgroundColor: Int? = null
    var foregroundColor: Int? = null

    fun setBackgroundColor(color: Int) = apply {
        this.backgroundColor = color
        replace("background", color.toHexColor())
    }

    fun setForegroundColor(color: Int) = apply {
        this.foregroundColor = color
        replace("foreground", color.toHexColor())
    }

    private fun replace(oldStr: String, newStr: String) {
        val file = "textmate/themes/one_dark.json"
        val content = file.getFileContentFromAssets(context).replace("$:$oldStr", newStr)
    }
}

fun Int.toHexColor(): String {
    val color = this.toString().replace("0x", "#")
    Log.d("color", color)
    return color
}