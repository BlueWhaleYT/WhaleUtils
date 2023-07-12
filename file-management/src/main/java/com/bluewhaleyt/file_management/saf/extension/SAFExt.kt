package com.bluewhaleyt.file_management.saf.extension

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Get the cursor of given [Uri]
 *
 * #### Example
 * ```kt
 * // Kotlin
 * val cursor = uri.getCursor(context)
 * ```
 *
 * ```java
 * // Java
 * Cursor cursor = SAFExtKt.getCursor(uri, context);
 * ```
 *
 * @param context
 * @param projection
 * @param selection
 * @param selectionArgs
 * @param sortOrder
 * @return the cursor of given [Uri]
 * @see ContentResolver.query
 */
fun Uri.getCursor(
    context: Context,
    projection: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    sortOrder: String? = null
): Cursor? {
    return context.contentResolver.query(this, projection, selection, selectionArgs, sortOrder)
}

/**
 * Get the content of given [Uri]
 *
 * @param context
 * @return the content of file from the given [Uri]
 * @see ContentResolver.openInputStream
 * @see BufferedReader.readLines
 */
fun Uri.getFileContent(context: Context): String {
    val inputStream = context.contentResolver.openInputStream(this)
    val reader = BufferedReader(InputStreamReader(inputStream))
    return reader.readLines().joinToString(separator = "\n")
}

/**
 * Get the file name of given [Uri]
 *
 * @param context
 * @return the file name
 * @see getCursor
 * @see Cursor.getColumnIndex
 * @see Cursor.getString
 */
fun Uri.getFileName(context: Context): String {
    val cursor = this.getCursor(context)!!
    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    cursor.moveToFirst()
    val name = cursor.getString(nameIndex)
    cursor.close()
    return name
}

/**
 * Get the file size
 *
 * @param context
 * @return the file size
 * @see getCursor
 * @see Cursor.getColumnIndex
 * @see Cursor.getString
 */
fun Uri.getFileSize(context: Context): String {
    val cursor = this.getCursor(context)!!
    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
    cursor.moveToFirst()
    val size = cursor.getString(sizeIndex)
    cursor.close()
    return size
}

/**
 * Get the file MIME type
 *
 * @param context
 * @return the MIME type of file
 * @see ContentResolver.getType
 */
fun Uri.getMIMEType(context: Context): String {
    return context.contentResolver.getType(this)!!
}