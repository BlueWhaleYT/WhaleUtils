package com.bluewhaleyt.file_management.saf.helper.uri

import android.content.Context
import android.net.Uri
import com.bluewhaleyt.file_management.saf.SAFUtils
import com.lazygeniouz.dfc.file.DocumentFileCompat

class SingleUriHelper(
    private val context: Context, saf: SAFUtils
) : UriHelper(context, saf) {

    override fun list(uri: Uri, extensions: Array<String>?): List<DocumentFileCompat>? {
        return null
    }

    override fun listRecursive(uri: Uri, extensions: Array<String>?): List<DocumentFileCompat>? {
        return null
    }
}