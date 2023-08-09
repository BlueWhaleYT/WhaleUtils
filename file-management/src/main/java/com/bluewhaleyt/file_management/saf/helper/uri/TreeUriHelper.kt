package com.bluewhaleyt.file_management.saf.helper.uri

import android.content.Context
import android.net.Uri
import android.util.Log
import com.bluewhaleyt.file_management.saf.SAFUtils
import com.bluewhaleyt.file_management.saf.getFileExtension
import com.bluewhaleyt.file_management.saf.isFile
import com.lazygeniouz.dfc.file.DocumentFileCompat
import java.util.Stack

class TreeUriHelper(
    private val context: Context,
    private val saf: SAFUtils
) : UriHelper(context, saf) {

    override fun list(uri: Uri, extensions: Array<String>?): List<DocumentFileCompat> {
        val dir = DocumentFileCompat.fromTreeUri(context, uri)!!
        return if (uri.isFile()) dir.listFiles().toList().filter { it.isFile() && (it.uri.getFileExtension() in extensions.orEmpty()) }
        else dir.listFiles().toList()
    }

    override fun listRecursive(uri: Uri, extensions: Array<String>?): List<DocumentFileCompat> {
        val dirs = mutableListOf<DocumentFileCompat>()
        val stack = Stack<DocumentFileCompat>()

        stack.push(DocumentFileCompat.fromTreeUri(context, uri))

        while (!stack.isEmpty()) {
            val dir = stack.pop()
            dirs.add(dir)
            for (file in dir.listFiles()) {
                if (file.isDirectory()) {
                    stack.push(file)
                }
                if (extensions.isNullOrEmpty() || saf.isFileWithExtensions(file, extensions)) {
                    dirs.add(file)
                }
            }
        }
        Log.d("dirs", dirs.toString())
        return dirs
    }

}