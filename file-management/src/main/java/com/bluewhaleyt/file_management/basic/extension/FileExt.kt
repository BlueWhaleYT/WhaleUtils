package com.bluewhaleyt.file_management.basic.extension

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import com.bluewhaleyt.file_management.basic.utils.FileUtils
import java.io.File

val fileUtils = FileUtils()

internal const val DEPRECATED_TO_REAL_FILE_PATH =
    """
    This function is deprecated because it uses file paths to handle file management, which may cause unexpected behavior or errors on newer versions of Android.
    The Uri to be strictly converted as an actual file path is not highly recommended. Use it at your own risk.
    
    Please use it for just DISPLAYING real file path to users.
    """

@Deprecated(
    message = DEPRECATED_TO_REAL_FILE_PATH,
    replaceWith = ReplaceWith(
        expression = "Uri-based file management. See the Android documentation on file management for more information.",
        imports = [
            "com.bluewhaleyt.file_management.saf.extension.SAFExtKt",
            "com.bluewhaleyt.file_management.saf.extension.SAFUtils"
        ]
    )
)
fun Uri.toRealFilePath(context: Context): String {
    return try {
        UriResolver.getPathFromUri(context, this)
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Uri is from document tree, please try to use Uri#toRealFilePathFromDocumentTree() instead.")
    }
}

@Deprecated(
    message = DEPRECATED_TO_REAL_FILE_PATH,
    replaceWith = ReplaceWith(
        expression = "Uri-based file management. See the Android documentation on file management for more information.",
        imports = [
            "com.bluewhaleyt.file_management.saf.extension.SAFExtKt",
            "com.bluewhaleyt.file_management.saf.extension.SAFUtils"
        ]
    )
)
fun Uri.toRealFilePathFromDocumentTree(context: Context): String {
    return try {
        UriResolver.getPathFromUri(context, this.toDocumentedUri())
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Uri is not from document tree, please try to use Uri#toRealFilePath() instead.")
    }
}

fun Uri.toDocumentedUri(): Uri {
    return DocumentsContract.buildDocumentUriUsingTree(this, DocumentsContract.getTreeDocumentId(this))
}

fun String.getFileContent(): String {
    val file = File(this)
    fileUtils.createNewFile(this)
    val sb = StringBuilder()
    file.reader().use { reader ->
        reader.forEachLine { line ->
            sb.append(line)
        }
    }
    return sb.toString()
}

fun String.getFileName(): String {
    return Uri.parse(this).lastPathSegment!!
}

fun String.getFileExtension(): String {
    var ext = ""
    val path = this.getFileName()
    if (path.isNotEmpty()) {
        val dot = path.indexOf(".")
        if (dot >= 0 && dot < path.length - 1) {
            ext = path.substring(dot + 1)
        }
    }
    return ext
}

fun String.getFileSize(): Long {
    return File(this).length()
}

fun String.getFileSizeStrength(max: Int = 100): Long {
    return (getFileSize() / 1024 / 1024) / max
}

fun String.isFileExist(): Boolean {
    return File(this).exists()
}

fun String.isFile(): Boolean {
    return File(this).isFile
}

fun String.isFileEmpty(): Boolean {
    return this.getFileContent().isEmpty()
}

fun String.isDirectory(): Boolean {
    return File(this).isDirectory
}

fun String.isDirectoryEmpty(): Boolean {
    val content: Array<out File>? = File(this).listFiles()
    return content?.size == 0
}

fun String.isFileHidden(): Boolean {
    return File(this).isHidden
}

fun String.isFileReadable(): Boolean {
    return File(this).canRead()
}

fun String.isFileWritable(): Boolean {
    return File(this).canWrite()
}

fun String.isFileExecutable(): Boolean {
    return File(this).canExecute()
}