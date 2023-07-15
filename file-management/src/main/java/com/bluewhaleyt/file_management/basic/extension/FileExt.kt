package com.bluewhaleyt.file_management.basic.extension

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import com.bluewhaleyt.file_management.basic.utils.FileUtils
import java.io.File

private val fileUtils = FileUtils()

internal const val DEPRECATED_TO_REAL_FILE_PATH =
    """
    This function is deprecated because it uses file paths to handle file management, which may cause unexpected behavior or errors on newer versions of Android.
    The Uri to be strictly converted as an actual file path is not highly recommended. Use it at your own risk.
    
    Please use it for just DISPLAYING real file path to users.
    """

/**
 * Gets the real file path from a [Uri].
 *
 * @receiver [Uri]
 *
 * @param context the context to use for resolving the [Uri].
 * @param fromDocumentTree indicates whether to use the DocumentTree [Uri] or not.
 *
 * @return the real file path from the [Uri].
 *
 * @see UriResolver.getPathFromUri
 * @see Uri.toDocumentedUri
 */
@Deprecated(
    message = DEPRECATED_TO_REAL_FILE_PATH,
    replaceWith = ReplaceWith(
        expression = "",
        imports = [
            "com.bluewhaleyt.file_management.saf.extension.SAFExtKt",
            "com.bluewhaleyt.file_management.saf.extension.SAFUtils"
        ]
    )
)
fun Uri.toRealFilePath(context: Context, fromDocumentTree: Boolean = false): String {
    val uri = if (fromDocumentTree) this.toDocumentedUri() else this
    return UriResolver.getPathFromUri(context, uri)
}

/**
 * Converts a [Uri] to a documented [Uri] by building [Uri] using [DocumentsContract.buildDocumentUriUsingTree]
 * and [DocumentsContract.getTreeDocumentId] methods.
 *
 * @receiver [Uri]
 * @return [Uri] object that represents a documented [Uri].
 *
 * @see DocumentsContract.buildDocumentUriUsingTree
 * @see DocumentsContract.getTreeDocumentId
 */
fun Uri.toDocumentedUri(): Uri {
    return DocumentsContract.buildDocumentUriUsingTree(this, DocumentsContract.getTreeDocumentId(this))
}

/**
 * Reads the content of a file specified by this string.
 *
 * @receiver the file path of [String]
 * @return the content of the file as a string, or an empty string if the file does not exist
 *
 * @see File
 * @see [FileUtils.createNewFile]
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
 * Returns the file name from a given file path string.
 *
 * @receiver the file path of [String]
 * @return The file name from the file path string.
 *
 * @see Uri.parse
 */
fun String.getFileName(): String {
    return Uri.parse(this).lastPathSegment!!
}

/**
 * Returns the file extension of the given string.
 *
 * @receiver the file path of [String]
 * @return the file extension, or an empty string if the file has no extension.
 *
 * @see String.getFileName
 * @see String.indexOf
 * @see String.substring
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
 * Returns the size of the file located at the given path.
 *
 * @receiver the file path of [String]
 * @return The size of the file in bytes.
 *
 * @see File.length
 */
fun String.getFileSize(): Long {
    return File(this).length()
}

/**
 * Calculates the 'file size strength' of the string, based on the maximum value provided.
 *
 * @receiver the file path of [String]
 * @param max The maximum value allowed for the file size strength. Default value is `100`.
 * @return The calculated file size strength of the string.
 *
 * @see String.getFileSize
 */
fun String.getFileSizeStrength(max: Int = 100): Long {
    return (getFileSize() / 1024 / 1024) / max
}

/**
 * Returns `true` if the file represented by this [String] exists in the file system, `false` otherwise.
 *
 * @receiver the file path of [String]
 * @return `true` if the file exists, `false` otherwise
 *
 * @see File.exists
 */
fun String.isFileExist(): Boolean {
    return File(this).exists()
}

/**
 * Returns `true` if the file represented by this [String] is a regular file, `false` otherwise.
 *
 * @receiver the file path of [String]
 * @return `true` if the file is a regular file, `false` otherwise
 *
 * @see File.isFile
 */
fun String.isFile(): Boolean {
    return File(this).isFile
}

/**
 * Returns `true` if the file represented by this [String] is empty, `false` otherwise.
 *
 * @receiver the file path of [String]
 * @return `true` if the file is empty, `false` otherwise
 *
 * @see getFileContent
 */
fun String.isFileEmpty(): Boolean {
    return this.getFileContent().isEmpty()
}

/**
 * Returns `true` if the file represented by this [String] is a directory, `false` otherwise.
 *
 * @receiver the file path of [String]
 * @return `true` if the file is a directory, `false` otherwise
 *
 * @see File.isDirectory
 */
fun String.isDirectory(): Boolean {
    return File(this).isDirectory
}

/**
 * Returns `true` if the directory represented by this [String] is empty, `false` otherwise.
 *
 * @receiver the file path of [String]
 * @return `true` if the directory is empty, `false` otherwise
 *
 * @see File.listFiles
 */
fun String.isDirectoryEmpty(): Boolean {
    val content: Array<out File>? = File(this).listFiles()
    return content?.size == 0
}

/**
 * Returns `true` if the file represented by this [String] is hidden, `false` otherwise.
 *
 * @receiver the file path of [String]
 * @return `true` if the file is hidden, `false` otherwise
 *
 * @see File.isHidden
 */
fun String.isFileHidden(): Boolean {
    return File(this).isHidden
}

/**
 * Returns `true` if the file represented by this [String] is readable, `false` otherwise.
 *
 * @receiver the file path of [String]
 * @return `true` if the file is readable, `false` otherwise
 *
 * @see File.canRead
 */
fun String.isFileReadable(): Boolean {
    return File(this).canRead()
}

/**
 * Returns `true` if the file represented by this [String] is writable, `false` otherwise.
 *
 * @receiver the file path of [String]
 * @return `true` if the file is writable, `false` otherwise
 *
 * @see File.canWrite
 */
fun String.isFileWritable(): Boolean {
    return File(this).canWrite()
}

/**
 * Returns `true` if the file represented by this [String] is executable, `false` otherwise.
 *
 * @receiver the file path of [String]
 * @return `true` if the file is executable, `false` otherwise
 *
 * @see File.canExecute
 */
fun String.isFileExecutable(): Boolean {
    return File(this).canExecute()
}