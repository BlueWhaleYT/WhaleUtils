package com.bluewhaleyt.file_management.saf

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bluewhaleyt.common.system.SDKUtils
import com.bluewhaleyt.file_management.core.FileUtils
import com.bluewhaleyt.file_management.core.getFileExtension
import com.bluewhaleyt.file_management.core.toDocumentedUri
import com.bluewhaleyt.file_management.saf.helper.uri.TreeUriHelper
import com.lazygeniouz.dfc.file.DocumentFileCompat

class SAFUtils(
    private val context: Context
): FileUtils(context) {

    private val treeUriHelper = TreeUriHelper(context, this)

    private val intentUriFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

    val intentOpenDocument: Intent get() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.apply {
            type = "*/*"
            flags = intentUriFlags
        }
        return intent
    }

    val intentOpenDocumentTree: Intent get() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent.flags = intentUriFlags
        return intent
    }

    private fun handleActivityResult(result: ActivityResult, callback: (uri: Uri?) -> Unit) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val uri = data?.data
            callback(uri)
        }
    }

    fun registerActivityResultLauncher(activity: AppCompatActivity, callback: (uri: Uri?) -> Unit): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleActivityResult(result, callback)
        }
    }

    @SuppressLint("WrongConstant")
    fun setPermanentAccess(data: Intent) {
        val uri: Uri?
        if (data.data.also { uri = it } != null) {
            val takeFlags = data.flags and intentUriFlags
            context.contentResolver.takePersistableUriPermission(uri!!, takeFlags)
        }
    }

    fun requestAllFileAccess(withNavigate: Boolean = true) {
        if (SDKUtils.isAtLeastSDK30) {
            if (!isExternalStorageGranted()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                if (withNavigate) {
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.setData(uri)
                }
                context.startActivity(intent)
            }
        } else {
            requestWritePermission()
        }
    }

//    private fun listFiles(uri: Uri, uriType: FileUriType, extensions: Array<String>? = null, recursive: Boolean = false): List<DocumentFileCompat> {
//        return when (uriType) {
//            FileUriType.TREE_URI -> ((if (recursive) listRecursive(uri, FileUriType.TREE_URI, extensions) else list(uri, FileUriType.TREE_URI, extensions))!!)
//            FileUriType.SINGLE_URI -> ((if (recursive) listRecursive(uri, FileUriType.SINGLE_URI, extensions) else list(uri, FileUriType.SINGLE_URI, extensions))!!)
//            FileUriType.CONTENT_URI -> ((if (recursive) listRecursive(uri, FileUriType.CONTENT_URI, extensions) else list(uri, FileUriType.CONTENT_URI, extensions))!!)
//
//            else -> throw IllegalArgumentException("Invalid URI type.")
//        }
//    }
//
//    fun listFiles(uri: Uri, filter: FileFilter = FileFilter.ALL, uriType: FileUriType = FileUriType.TREE_URI, extensions: Array<String>? = null, recursive: Boolean = false): List<DocumentFileCompat> {
//        return when (filter) {
//            FileFilter.ALL -> listFiles(uri, uriType, extensions, recursive)
//            FileFilter.ONLY_DIRS -> listFiles(uri, uriType, extensions, recursive).filter { it.isDirectory() }
//            FileFilter.ONLY_FILES -> listFiles(uri, uriType, extensions, recursive).filter { it.isFile() }
//            FileFilter.ONLY_VIRTUAL -> listFiles(uri, uriType, extensions, recursive).filter { it.isVirtual() }
//            FileFilter.ONLY_HIDDEN -> listFiles(uri, uriType, extensions, recursive).filter { it.isFile() && it.name?.startsWith(".") == true }
//        }
//    }
//
//    fun listFiles(file: File): List<DocumentFileCompat> {
//        return DocumentFileCompat.fromFile(context, file).listFiles().toList()
//    }
//
//    private fun list(uri: Uri, uriType: FileUriType, extensions: Array<String>?): List<DocumentFileCompat>? {
//        return when (uriType) {
//            FileUriType.TREE_URI -> treeUriHelper.list(uri, extensions)
//            else -> throw IllegalArgumentException("Invalid URI type.")
//        }
//    }
//
//    private fun listRecursive(uri: Uri, uriType: FileUriType, extensions: Array<String>?): List<DocumentFileCompat>? {
//        return when (uriType) {
//            FileUriType.TREE_URI -> treeUriHelper.listRecursive(uri, extensions)
//            else -> throw IllegalArgumentException("Invalid URI type.")
//        }
//    }

    fun getExternalStorageUri(): Uri {
        return if (SDKUtils.isAtLeastSDK29) MediaStore.Downloads.EXTERNAL_CONTENT_URI
        else Uri.parse(Environment.getExternalStorageDirectory().path)
    }


//    fun listExternalStorage(): List<DocumentFileCompat> {
//        return listFiles(Environment.getExternalStorageDirectory())
//    }

    fun getExternalStorageTreeUri(): Uri {
        val rootUri = DocumentsContract.buildRootsUri("com.android.externalstorage.documents")
        return rootUri.toDocumentedUri()
    }

    fun isFileWithExtensions(file: DocumentFileCompat, extensions: Array<String>): Boolean {
        val fileName = file.name
        val fileExtension = fileName.getFileExtension()
        return extensions.any { it.equals(fileExtension, ignoreCase = true) }
    }

    fun hasValidExtension(fileName: String?, extensions: Array<String>?): Boolean {
        if (fileName != null && extensions != null) {
            for (extension in extensions) {
                if (fileName.endsWith(extension, true)) {
                    return true
                }
            }
        }
        return false
    }

}