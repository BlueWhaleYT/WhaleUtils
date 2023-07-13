package com.bluewhaleyt.whaleutils.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bluewhaleyt.whaleutils.ui.screen.file.FileManagerScreen
import com.bluewhaleyt.whaleutils.ui.screen.file.editor.EditorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar() },
        content = {
            Content(navController)
            Modifier.padding(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    TopAppBar(
        title = {
            Text(text = "File Manager")
        }
    )
}

@Composable
fun Content(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.FileManager.MAIN.name
    ) {
        composable(Route.FileManager.MAIN.name) {
            FileManagerScreen(navController)
        }
        composable(Route.FileManager.EDITOR.name) {
            EditorScreen(navController)
        }
    }
}