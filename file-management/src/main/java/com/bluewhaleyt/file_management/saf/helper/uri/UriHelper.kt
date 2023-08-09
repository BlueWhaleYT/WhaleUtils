package com.bluewhaleyt.file_management.saf.helper.uri

import android.content.Context
import android.net.Uri
import com.bluewhaleyt.file_management.saf.SAFUtils
import com.lazygeniouz.dfc.file.DocumentFileCompat

abstract class UriHelper(
    private val context: Context,
    private val saf: SAFUtils
) {
    abstract fun list(uri: Uri, extensions: Array<String>?): List<DocumentFileCompat>?
    abstract fun listRecursive(uri: Uri, extensions: Array<String>?): List<DocumentFileCompat>?
}