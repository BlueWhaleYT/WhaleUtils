package com.bluewhaleyt.file_management.saf.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.bluewhaleyt.file_management.basic.utils.FileUtils
import com.bluewhaleyt.file_management.saf.extension.getFileContent
import java.io.File
import java.util.Stack

class SAFUtils(
    private val context: Context
) : FileUtils() {

    /**
     * A value that holds the flags for granting read and write permissions to a URI in an Intent.
     * The value is initialized with the bitwise or of two flag values:
     * [Intent.FLAG_GRANT_READ_URI_PERMISSION] and [Intent.FLAG_GRANT_WRITE_URI_PERMISSION].
     */
    private val intentUriFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

    /**
     * Returns an intent for opening a document.
     *
     * @return The intent for opening a document.
     *
     * @see Intent.ACTION_OPEN_DOCUMENT
     */
    val intentOpenDocument: Intent get() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "*/*"
        intent.flags = intentUriFlags
        return intent
    }

    /**
     * Returns an intent for opening a document.
     *
     * @return The intent for opening a document.
     *
     * @see Intent.ACTION_OPEN_DOCUMENT_TREE
     */
    val intentOpenDocumentTree: Intent get() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent.flags = intentUriFlags
        return intent
    }

    /**
     * Requests access to all files on external storage.
     * If current SDK is lower than [Build.VERSION_CODES.R], will call [FileUtils.requestWritePermission] instead.
     *
     * @param withNavigate Whether to navigate to the part of current package if permission is not granted.
     *
     * @see Uri.fromParts
     * @see FileUtils.requestWritePermission
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
            requestWritePermission(context)
        }
    }

    /**
     * Grants permanent access to a URI to the calling application.
     *
     * @param data The intent containing the [Uri] data.
     *
     * @see Context.getContentResolver
     */
    @SuppressLint("WrongConstant")
    fun setPermanentAccess(data: Intent) {
        val uri: Uri?
        if (data.data.also { uri = it } != null) {
            val takeFlags = data.flags and intentUriFlags
            context.contentResolver.takePersistableUriPermission(uri!!, takeFlags)
        }
    }

    private fun handleActivityResult(result: ActivityResult, callback: (uri: Uri?) -> Unit) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val uri = data?.data
            callback(uri)
        }
    }

    fun registerActivityResultLauncher(activity: ComponentActivity, callback: (uri: Uri?) -> Unit): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleActivityResult(result, callback)
        }
    }

    fun registerActivityResultLauncher(activity: AppCompatActivity, callback: (uri: Uri?) -> Unit): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleActivityResult(result, callback)
        }
    }

    fun readFile(uri: Uri): String {
        return uri.getFileContent(context)
    }

    fun listDirectories(uri: Uri): List<DocumentFile> {
        val dir = DocumentFile.fromTreeUri(context, uri)!!
        return dir.listFiles().toList()
    }

    fun listDirectoriesSubDir(uri: Uri): List<DocumentFile> {
        val dirs = mutableListOf<DocumentFile>()
        val stack = Stack<DocumentFile>()
        stack.push(DocumentFile.fromTreeUri(context, uri))
        while (!stack.isEmpty()) {
            val dir = stack.pop()
            dirs.add(dir)
            for (file in dir.listFiles()) {
                if (file.isDirectory) {
                    stack.push(file)
                }
                dirs.add(file)
            }
        }
        return dirs
    }

    fun listOnlyDirectories(uri: Uri): List<DocumentFile> {
        return listDirectories(uri).filter { it.isDirectory }
    }

    fun listOnlyFiles(uri: Uri): List<DocumentFile> {
        return listDirectories(uri).filter { it.isFile }
    }

    fun listOnlyDirectoriesSubDir(uri: Uri): List<DocumentFile> {
        return listDirectoriesSubDir(uri).filter { it.isDirectory }
    }

    fun listOnlyFilesSubDir(uri: Uri): List<DocumentFile> {
        return listDirectoriesSubDir(uri).filter { it.isFile }
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