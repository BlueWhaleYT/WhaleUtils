package com.bluewhaleyt.file_management.basic.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import androidx.core.app.ActivityCompat
import com.bluewhaleyt.common.system.utils.SDKUtils
import com.bluewhaleyt.file_management.basic.extension.getFileContent
import com.bluewhaleyt.file_management.basic.extension.isFileExist
import com.bluewhaleyt.file_management.saf.utils.SAFUtils
import java.io.File
import java.io.FileFilter
import java.io.FileWriter
import java.io.IOException

open class FileUtils {

    companion object {
        private const val PERM_REQ_CODE_READ_WRITE = 1000
    }

    val externalStorageDirPath: String = Environment.getExternalStorageDirectory().path

    val dataDirPath: String = Environment.getDataDirectory().path

    val androidDataDirPath: String = "${externalStorageDirPath}/Android/data"

    val androidObbDirPath: String = "${externalStorageDirPath}/Android/obb"

    fun isGrantedExternalStorageAccess(): Boolean {
        return if (SDKUtils.isAtLeastSDK30) {
            Environment.isExternalStorageManager()
        } else false
    }

    fun requestPermission(context: Context, permissions: Array<String>, requestCode: Int) {
        context.startActivity(Intent(context, context.javaClass))
        ActivityCompat.requestPermissions(context as Activity, permissions, requestCode)
    }

    fun requestWritePermission(context: Context) {
        requestPermission(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_REQ_CODE_READ_WRITE)
    }

    fun requestReadPermission(context: Context) {
        requestPermission(context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERM_REQ_CODE_READ_WRITE)
    }

    fun createNewDirectory(path: String) {
        if (!path.isFileExist()) {
            val file = File(path)
            file.mkdirs()
        }
    }

    fun createNewFile(path: String) {
        val lastSep = path.lastIndexOf(File.separator)
        if (lastSep > 0) {
            val dirPath = path.substring(0, lastSep)
            createNewDirectory(dirPath)
        }
        val file = File(path)
        try {
            if (!file.exists()) file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun writeFile(path: String, content: String) {
        createNewFile(path)
        FileWriter(path, false).use { it.write(content) }
    }

    fun readFile(path: String): String {
        return path.getFileContent()
    }

    fun listDirectories(path: String, fileFilter: FileFilter): List<String>? {
        val dir = File(path)
        if (!dir.exists() || dir.isFile) {
            return null
        }
        val listFiles: Array<File>? = dir.listFiles(fileFilter)
        if (listFiles.isNullOrEmpty()) {
            return null
        }
        val fileList: MutableList<String> = ArrayList()
        for (file in listFiles) {
            fileList.add(file.name)
        }
        return fileList
    }

}