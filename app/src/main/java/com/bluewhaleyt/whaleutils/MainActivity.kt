package com.bluewhaleyt.whaleutils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bluewhaleyt.compose_common.color.utils.colorSurface
import com.bluewhaleyt.whaleutils.ui.theme.WhaleUtilsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhaleUtilsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorSurface
                ) {
                    val context = LocalContext.current

                }
            }
        }
    }
}