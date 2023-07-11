package com.bluewhaleyt.file_management.basic.utils

import android.os.Build
import android.os.Environment
import com.bluewhaleyt.file_management.basic.extension.getFileContent
import com.bluewhaleyt.file_management.basic.extension.isFileExist
import java.io.File
import java.io.FileFilter
import java.io.FileWriter
import java.io.IOException

/**
 * FileUtils aims to easily manage and handle file managing operations.
 *
 * @constructor Create empty File utils
 */
open class FileUtils {
//    companion object {

        /**
         * External storage directory path i.e. `/sdcard` or `/storage/emulated/0`
         *
         * @see [Environment.getExternalStorageDirectory]
         */
        val externalStorageDirPath: String = Environment.getExternalStorageDirectory().path

        /**
         * Data directory path i.e. /data/
         * @see Environment.getDataDirectory
         */
        val dataDirPath: String = Environment.getDataDirectory().path

        /**
         * Android data directory path
         * @see externalStorageDirPath
         */
        val androidDataDirPath: String = "${externalStorageDirPath}/Android/data"

        /**
         * Android obb directory path
         * @see externalStorageDirPath
         */
        val androidObbDirPath: String = "${externalStorageDirPath}/Android/obb"

        /**
         * Determine if the permission of external storage managing is granted on current devices
         *
         * @return `true` if the permission is granted, `false` otherwise
         * @see Environment.isExternalStorageManager
         */
        fun isGrantedExternalStorageAccess(): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager()
            } else false
        }

        /**
         * Create a new directory
         *
         * @param path
         * @see [File.mkdirs]
         */
        fun createNewDirectory(path: String) {
            if (!path.isFileExist()) {
                val file = File(path)
                file.mkdirs()
            }
        }

        /**
         * Create a new file
         *
         * @param path
         * @see [File.createNewFile]
         * @see [createNewDirectory]
         */
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

        /**
         * Create and write content to an exist file
         *
         * @param path
         * @param content the default content to be written
         * @see [FileWriter.write]
         * @see [createNewFile]
         */
        fun writeFile(path: String, content: String) {
            createNewFile(path)
            FileWriter(path, false).use { it.write(content) }
        }

        /**
         * Read the content of file
         *
         * @param path
         * @return the content of file
         * @see [getFileContent]
         */
        fun readFile(path: String): String {
            return path.getFileContent()
        }

        /**
         * List directories including all files and directories
         *
         * @param path
         * @param fileFilter
         * @return the list of files if the given path is valid
         * @see File.listFiles
         */
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

//    }
}