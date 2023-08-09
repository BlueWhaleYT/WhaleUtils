package com.bluewhaleyt.file_management.core

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.Locale

fun Uri.toDocumentedUri(): Uri {
    return DocumentsContract.buildDocumentUriUsingTree(this, DocumentsContract.getTreeDocumentId(this))
}

fun Uri.toRealFilePath(context: Context, fromDocumentTree: Boolean = false): String {
    val uri = if (fromDocumentTree) this.toDocumentedUri() else this
    return UriResolver.getPathFromUri(context, uri).toString()
}

fun String.getParentPath(): String? {
    return File(this).parent
}

fun String.getFileExtension(): String? {
    val dotIndex = this.lastIndexOf('.')
    return if (dotIndex > 0 && dotIndex < this.length - 1) {
        this.substring(dotIndex + 1)
    } else null
}

fun String.getFileContent(wordWrap: Boolean = true): String {
    val file = File(this)
    val reader = BufferedReader(FileReader(file))
    return if (wordWrap) reader.readLines().joinToString("\n")
    else reader.readLines().toString()
}

fun String.getFileContentLineCount(): Int {
    val file = File(this)
    val reader = BufferedReader(FileReader(file))
    return reader.readLines().size
}

fun List<File>.basicSort(): List<File> {
    return this.sortedWith(compareBy(
        { if (it.isDirectory) 0 else 1 },
        { it.name.lowercase(Locale.ROOT) }
    ))
}