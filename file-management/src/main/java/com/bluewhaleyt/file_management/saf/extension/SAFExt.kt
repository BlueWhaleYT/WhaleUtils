package com.bluewhaleyt.file_management.saf.extension

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Returns a [Cursor] object for a given [Uri] with optional parameters.
 *
 * @receiver [Uri]
 *
 * @param context the [Context] used to query the content.
 * @param projection the list of columns to return. Passing `null` will return all columns.
 * @param selection the filter query to apply. Passing `null` will return all rows.
 * @param selectionArgs the values to replace the placeholders in the `selection` parameter.
 * @param sortOrder the order in which to return the rows. Passing `null` will return the rows in default order.
 *
 * @return the [Cursor] object for the given [Uri], or `null` if the query fails.
 * @see Context.getContentResolver
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
 * Returns the content of a file from the given Uri.
 *
 * @receiver [Uri]
 * @param context The context of the application.
 * @return The content of the file in the form of a String.
 *
 * @see Context.getContentResolver
 * @see BufferedReader.readLines
 */
fun Uri.getFileContent(context: Context): String {
    val inputStream = context.contentResolver.openInputStream(this)
    val reader = BufferedReader(InputStreamReader(inputStream))
    return reader.readLines().joinToString(separator = "\n")
}

/**
 * Returns the name of a file from the given Uri.
 *
 * @receiver [Uri]
 * @param context The context of the application.
 * @return The name of the file.
 *
 * @see Uri.getCursor
 * @see Cursor.getColumnIndex
 * @see OpenableColumns.DISPLAY_NAME
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
 * Returns the size of a file from the given Uri.
 *
 * @receiver [Uri]
 * @param context The context of the application.
 * @return The size of the file as a String.
 *
 * @see Uri.getCursor
 * @see Cursor.getColumnIndex
 * @see OpenableColumns.SIZE
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
 * Returns the MIME type of a file from the given Uri.
 *
 * @receiver [Uri]
 * @param context The context of the application.
 * @return The MIME type of the file.
 * @see Context.getContentResolver
 */
fun Uri.getMIMEType(context: Context): String {
    return context.contentResolver.getType(this)!!
}