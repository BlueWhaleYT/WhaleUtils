package com.bluewhaleyt.file_management.saf

import android.content.Context
import android.net.Uri
import android.util.Log
import com.lazygeniouz.dfc.file.DocumentFileCompat
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.Locale

fun Uri.getFileExtension(): String? {
    val path = this.path
    val dotIndex = path?.lastIndexOf('.')
    return if (dotIndex != null && dotIndex > 0 && dotIndex < path.length - 1) {
        val ext = path.substring(dotIndex + 1)
        Log.d("ext", ext)
        ext
    } else null
}

fun Uri.getFileContent(context: Context, wordWrap: Boolean = true): String {
    val inputStream = context.contentResolver.openInputStream(this)
    val reader = BufferedReader(InputStreamReader(inputStream))
    return if (wordWrap) reader.readLines().joinToString("\n")
    else reader.readLines().toString()
}

fun Uri.isFile(): Boolean {
    return File(this.path.toString()).isFile
}

fun List<DocumentFileCompat>.basicSort(): List<DocumentFileCompat> {
    return this.sortedWith(compareBy(
        { if (it.isDirectory()) 0 else 1 },
        { it.name.lowercase(Locale.ROOT) }
    ))
}