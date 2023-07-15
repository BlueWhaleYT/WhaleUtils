package com.bluewhaleyt.whaleutils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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