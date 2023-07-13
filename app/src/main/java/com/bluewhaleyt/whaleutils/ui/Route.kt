package com.bluewhaleyt.whaleutils.ui

sealed class Routes(val route: String) {
    class FileManager {
        object Main : Routes("fm_main")
        object Editor : Routes("fm_editor")
    }
}