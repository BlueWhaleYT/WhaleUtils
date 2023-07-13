package com.bluewhaleyt.whaleutils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bluewhaleyt.compose_common.color.utils.colorSurface
import com.bluewhaleyt.file_management.basic.extension.toRealFilePathFromDocumentTree
import com.bluewhaleyt.file_management.saf.utils.SAFUtils
import com.bluewhaleyt.whaleutils.ui.App
import com.bluewhaleyt.whaleutils.ui.theme.WhaleUtilsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhaleUtilsTheme {
                App()
            }
        }
    }

}