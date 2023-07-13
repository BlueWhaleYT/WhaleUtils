package com.bluewhaleyt.whaleutils.ui.screen.file.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun EditorScreen(navController: NavController) {
    val context = LocalContext.current

    Column {
        Text(
            text = "123"
        )
    }
}