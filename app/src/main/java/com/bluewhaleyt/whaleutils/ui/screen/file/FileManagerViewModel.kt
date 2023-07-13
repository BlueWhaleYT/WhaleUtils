package com.bluewhaleyt.whaleutils.ui.screen.file

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import com.bluewhaleyt.file_management.basic.extension.toRealFilePath
import com.bluewhaleyt.file_management.saf.utils.SAFUtils

class FileManagerViewModel(
    private val context: Context
) : ViewModel() {

    lateinit var fileItem: DocumentFile

    val saf = SAFUtils(context)
    val files = saf.listExternalStorage()

    fun grantPermission() {
        saf.requestAllFileAccess()
    }

    fun isFile(): Boolean {
        return fileItem.isFile
    }

    fun getFileIcon(): ImageVector {
        return if (isFile()) Icons.Default.InsertDriveFile else Icons.Default.Folder
    }

    @Composable
    fun getFileNameColor(): Color {
        return if (isFile()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
    }

}