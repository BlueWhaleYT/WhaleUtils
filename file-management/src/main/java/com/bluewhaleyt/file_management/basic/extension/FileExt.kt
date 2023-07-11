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

/**
 * Convert the given [Uri] to actual file path
 *
 * #### Example
 * ```kt
 * // Kotlin
 * val filePath = uri.toRealFilePath()
 * ```
 *
 * ```java
 * // Java
 * String filePath = FileExtKt.toRealFilePath(uri);
 * ```
 *
 * @param context
 * @return the actual file path if given [Uri] is valid
 * @see [UriResolver.getPathFromUri]
 */
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

/**
 * Convert the given [Uri] to actual file path from document tree
 *
 * #### Example
 * ```kt
 * // Kotlin
 * val filePath = uri.toRealFilePathFromDocumentTree()
 * ```
 *
 * ```java
 * // Java
 * String filePath = FileExtKt.toRealFilePathFromDocumentTree(uri);
 * ```
 *
 * @param context
 * @return the actual file path if given [Uri] is valid
 * @see [UriResolver.getPathFromUri]
 * @see [Uri.toDocumentedUri]
 */
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

/**
 * Convert the given [Uri] to documented uri
 *
 * @return the documented uri
 * @see [DocumentsContract.buildDocumentUriUsingTree]
 * @see [DocumentsContract.getTreeDocumentId]
 */
fun Uri.toDocumentedUri(): Uri {
    return DocumentsContract.buildDocumentUriUsingTree(this, DocumentsContract.getTreeDocumentId(this))
}


/**
 * Get the content of given file path
 *
 * #### Example
 * ```kt
 * // Kotlin
 * val content = filePath.getFileContent()
 * ```
 *
 * ```java
 * // Java
 * String content = FileExtKt.getFileContent(filePath);
 * ```
 *
 * @return the file content if file is valid
 * @see [FileUtils.readFile]
 */
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

/**
 * Get the file name of given file path
 *
 * @return the file name, empty if the file path is invalid
 * @see [Uri.parse]
 */
fun String.getFileName(): String {
    return Uri.parse(this).lastPathSegment!!
}

/**
 * Get the file extension of given file path
 *
 * @return the file extension, empty if file path is invalid
 * @see [getFileName]
 */
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

/**
 * Get the file size of given file path
 *
 * @return the file size, `0f` if file path is invalid
 * @see [File.length]
 */
fun String.getFileSize(): Long {
    return File(this).length()
}

/**
 * Get the strength level of file size
 *
 * @param max
 * @return the strength level, `0f` if file path is invalid
 * @receiver [String]
 *
 * #### Example
 * ```kt
 * // Kotlin
 * val strength = filePath.getFileSizeStrength()
 * val color =
 *     if (strength < 0.33) Color.GREEN
 *     else if (strength < 0.67) Color.YELLOW
 *     else Color.RED
 * ```
 */
fun String.getFileSizeStrength(max: Int = 100): Long {
    return (getFileSize() / 1024 / 1024) / max
}

/**
 * Determine if the given file is exist
 *
 * @return `true` if file is exist, `false` otherwise
 * @see [File.exists]
 */
fun String.isFileExist(): Boolean {
    return File(this).exists()
}

/**
 * Determine if the given file path is a file type
 *
 * @return `true` if it's file, `false` otherwise
 * @see [File.isFile]
 */
fun String.isFile(): Boolean {
    return File(this).isFile
}

/**
 * Determine if the given file path's content is empty
 *
 * @return `true` if file is empty, `false` otherwise
 * @see [getFileContent]
 */
fun String.isFileEmpty(): Boolean {
    return this.getFileContent().isEmpty()
}

/**
 * Determine if the given file path is a directory type
 *
 * @return `true` if it's directory, `false` otherwise
 * @see [File.isDirectory]
 */
fun String.isDirectory(): Boolean {
    return File(this).isDirectory
}

/**
 * Determine if the given file path's directory is empty
 *
 * @return `true` if directory is empty, `false` otherwise
 * @see [File.listFiles]
 */
fun String.isDirectoryEmpty(): Boolean {
    val content: Array<out File>? = File(this).listFiles()
    return content?.size == 0
}

/**
 * Determine if the given file path is hidden
 *
 * @return `true` if the file is hidden, `false` otherwise
 * @see [File.isHidden]
 */
fun String.isFileHidden(): Boolean {
    return File(this).isHidden
}

/**
 * Determine if the given file path is readable
 *
 * @return `true` if the file is readable, `false` otherwise
 * @see [File.canRead]
 */
fun String.isFileReadable(): Boolean {
    return File(this).canRead()
}

/**
 * Determine if the given file path is writable
 *
 * @return `true` if the file is writable, `false` otherwise
 * @see [File.canWrite]
 */
fun String.isFileWritable(): Boolean {
    return File(this).canWrite()
}

/**
 * Determine if the given file path is executable
 *
 * @return `true` if the file is executable, `false` otherwise
 * @see [File.canExecute]
 */
fun String.isFileExecutable(): Boolean {
    return File(this).canExecute()
}