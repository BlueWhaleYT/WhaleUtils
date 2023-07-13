package com.bluewhaleyt.whaleutils.ui.screen.file.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.bluewhaleyt.file_management.saf.extension.getFileContent

@Composable
fun EditorScreen(
    navController: NavController,
    uriString: String
) {
    val context = LocalContext.current
    val content = uriString.toUri().getFileContent(context)
    var text by remember { mutableStateOf(content) }

    Column {
        TextField(
            value = text,
            onValueChange = { text = it }
        )
    }
}