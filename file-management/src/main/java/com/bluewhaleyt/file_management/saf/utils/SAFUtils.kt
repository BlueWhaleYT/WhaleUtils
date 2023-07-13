package com.bluewhaleyt.file_management.saf.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.documentfile.provider.DocumentFile
import com.bluewhaleyt.file_management.basic.extension.fileUtils
import com.bluewhaleyt.file_management.basic.utils.FileUtils
import com.bluewhaleyt.file_management.saf.extension.getFileContent
import java.io.File
import java.util.Stack
import java.util.stream.Collectors.toList

class SAFUtils(
    private val context: Context
) : FileUtils() {

    private val intentUriFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

    val intentOpenDocument: Intent get() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "*/*"
        intent.flags = intentUriFlags
        return intent
    }

    val intentOpenDocumentTree: Intent get() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent.flags = intentUriFlags
        return intent
    }

    /**
     * Request All File Access permission
     *
     * #### Example
     * ```kt
     * // Kotlin
     * val saf = SAFUtils(context)
     * saf.requestAllFileAccess()
     * ```
     *
     * ```java
     * // Java
     * SAFUtils saf = new SAFUtils(context);
     * saf.requestAllFileAccess(true);
     * ```
     *
     * @param withNavigate navigate to the current package
     * @see Uri.fromParts
     */
    fun requestAllFileAccess(withNavigate: Boolean = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!isGrantedExternalStorageAccess()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                if (withNavigate) {
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.setData(uri)
                }
                context.startActivity(intent)
            }
        } else {
            fileUtils.requestWritePermission(context)
        }
    }

    /**
     * Set permanent access
     *
     * @param data
     */
    @SuppressLint("WrongConstant")
    fun setPermanentAccess(data: Intent) {
        val uri: Uri?
        if (data.data.also { uri = it } != null) {
            val takeFlags = data.flags and intentUriFlags
            context.contentResolver.takePersistableUriPermission(uri!!, takeFlags)
        }
    }

    /**
     * Register activity result launcher
     *
     * #### Example
     * ```kt
     * // Kotlin
     * val saf = SAFUtils(context)
     * val launcher = saf.registerActivityResultLauncher(activity) { uri ->
     *     val filePath = uri.toRealFilePath(context)
     * }
     * ```
     *
     * ```java
     * // Java
     * SAFUtils saf = new SAFUtils(this);
     * ActivityResultLauncher<Intent> launcher = saf.registerActivityResultLauncher(activity, uri -> {
     *     String filePath = FileExtKt.toRealFilePath(uri, this);
     *     return null;
     * });
     * ```
     *
     * @param callback
     */
    fun registerActivityResultLauncher(activity: ComponentActivity, callback: (uri: Uri?) -> Unit): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                callback(uri)
            }
        }
    }

    fun registerActivityResultLauncher(activity: AppCompatActivity, callback: (uri: Uri?) -> Unit): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                callback(uri)
            }
        }
    }

    fun readFile(uri: Uri): String {
        return uri.getFileContent(context)
    }

    fun listDirectories(uri: Uri, filter: ((DocumentFile) -> Boolean)? = null): List<DocumentFile> {
        val dir = DocumentFile.fromTreeUri(context, uri)!!
        return if (filter == null) {
            dir.listFiles().toList()
        } else {
            dir.listFiles().filter(filter)
        }
    }

    fun listDirectoriesSubDir(uri: Uri, filter: ((DocumentFile) -> Boolean)? = null): List<DocumentFile> {
        val dirs = mutableListOf<DocumentFile>()
        val stack = Stack<DocumentFile>()
        stack.push(DocumentFile.fromTreeUri(context, uri))
        while (!stack.isEmpty()) {
            val dir = stack.pop()
            if (filter == null || filter(dir)) {
                dirs.add(dir)
            }
            for (file in dir.listFiles()) {
                if (file.isDirectory) {
                    stack.push(file)
                } else if (filter == null || filter(file)) {
                    dirs.add(file)
                }
            }
        }
        return dirs
    }

    fun listOnlyDirectories(uri: Uri): List<DocumentFile> {
        return listDirectories(uri) { it.isDirectory }
    }

    fun listOnlyFiles(uri: Uri): List<DocumentFile> {
        return listDirectories(uri) { it.isFile }
    }

    fun listOnlyDirectoriesSubDir(uri: Uri): List<DocumentFile> {
        return listDirectoriesSubDir(uri) { it.isDirectory }
    }

    fun listOnlyFilesSubDir(uri: Uri): List<DocumentFile> {
        return listDirectoriesSubDir(uri) { it.isFile }
    }

    fun listFromFile(file: File): List<DocumentFile> {
        return DocumentFile.fromFile(file).listFiles().toList()
    }

    fun listFromTreeUri(uri: Uri): List<DocumentFile> {
        val treeUri = DocumentFile.fromTreeUri(context, uri)!!
        return treeUri.listFiles().toList()
    }

    fun listFromSingleUri(uri: Uri): List<DocumentFile> {
        val singleUri = DocumentFile.fromSingleUri(context, uri)!!
        return singleUri.listFiles().toList()
    }

    fun listExternalStorage(): List<DocumentFile> {
        return listFromFile(Environment.getExternalStorageDirectory())
    }

}