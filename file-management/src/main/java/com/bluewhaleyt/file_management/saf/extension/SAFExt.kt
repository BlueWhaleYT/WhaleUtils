package com.bluewhaleyt.file_management.saf.extension

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import java.io.BufferedReader
import java.io.InputStreamReader

fun Uri.getCursor(
    context: Context,
    projection: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    sortOrder: String? = null
): Cursor? {
    return context.contentResolver.query(this, projection, selection, selectionArgs, sortOrder)
}

fun Uri.getFileContent(context: Context): String {
    val inputStream = context.contentResolver.openInputStream(this)
    val reader = BufferedReader(InputStreamReader(inputStream))
    return reader.readLines().joinToString(separator = "\n")
}

fun Uri.getFileName(context: Context): String {
    val cursor = this.getCursor(context)!!
    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    cursor.moveToFirst()
    val name = cursor.getString(nameIndex)
    cursor.close()
    return name
}

fun Uri.getFileSize(context: Context): String {
    val cursor = this.getCursor(context)!!
    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
    cursor.moveToFirst()
    val size = cursor.getString(sizeIndex)
    cursor.close()
    return size
}

fun Uri.getMIMEType(context: Context): String {
    return context.contentResolver.getType(this)!!
}