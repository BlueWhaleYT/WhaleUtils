package com.bluewhaleyt.file_management.search

import android.text.SpannableString

data class SearchResult(
    val filePath: String,
    val fileName: String,
    val fileContent: String,
    val highlightedContent: SpannableString,
    val lineNumber: Int
)
