package com.bluewhaleyt.file_management.core

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bluewhaleyt.common.system.SDKUtils
import java.io.File




open class FileUtils(
    private val context: Context
) {

    companion object {
        private const val PERM_REQ_CODE_READ_WRITE = 1000
    }

    val externalStoragePath get() = Environment.getExternalStorageDirectory().path

    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun isExternalStorageManager(): Boolean {
        return if (SDKUtils.isAtLeastSDK30) Environment.isExternalStorageManager()
        else false
    }

    fun isExternalStorageGranted(): Boolean {
        return if (SDKUtils.isAtLeastSDK30) isExternalStorageManager()
        else isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun requestPermission(permissions: Array<String>, requestCode: Int) {
        context.startActivity(Intent(context, context.javaClass))
        ActivityCompat.requestPermissions(context as Activity, permissions, requestCode)
    }

    fun requestWritePermission() {
        requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_REQ_CODE_READ_WRITE)
    }

    fun requestReadPermission() {
        requestPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERM_REQ_CODE_READ_WRITE)
    }

    fun listFiles(path: String, fileFilter: FileFilter = FileFilter.ALL, recursive: Boolean = false): List<File> {
        val file = File(path)
        val list = mutableListOf<File>()
        val directoryList = mutableListOf<File>()

        if (file.isDirectory) {
            val files = file.listFiles()

            files?.let {
                for (currentFile in files) {
                    if (currentFile.isDirectory) {
                        if (recursive) {
                            val subList = listFiles(currentFile.path, fileFilter)
                            list.addAll(subList)
                        }
                        if (when (fileFilter) {
                                FileFilter.ALL -> true
                                FileFilter.ONLY_DIRS -> true
                                FileFilter.ONLY_HIDDEN -> currentFile.isHidden
                                else -> false
                            }) {
                            directoryList.add(currentFile)
                        }
                    } else {
                        if (when (fileFilter) {
                                FileFilter.ALL -> true
                                FileFilter.ONLY_FILES -> true
                                FileFilter.ONLY_HIDDEN -> currentFile.isHidden
                                else -> false
                            }) {
                            list.add(currentFile)
                        }
                    }
                }
            }
        }
        list.addAll(directoryList)
        return list
    }
}