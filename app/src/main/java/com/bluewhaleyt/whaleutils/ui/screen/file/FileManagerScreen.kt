package com.bluewhaleyt.whaleutils.ui.screen.file

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.documentfile.provider.DocumentFile
import androidx.navigation.NavController
import com.bluewhaleyt.file_management.basic.extension.toRealFilePath
import com.bluewhaleyt.whaleutils.ui.Route
import com.bluewhaleyt.whaleutils.ui.theme.spacing

@Composable
fun FileManagerScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = FileManagerViewModel(context)

    Column(
    ) {
        if (viewModel.saf.isGrantedExternalStorageAccess()) {
            FileList(context, viewModel, navController)
        } else {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.grantPermission() }
            ) {
                Text(text = "Grant permission")
            }
        }

    }

}

@Composable
fun FileList(context: Context, viewModel: FileManagerViewModel, navController: NavController) {
    if (viewModel.files.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(viewModel.files) { _, item ->
                viewModel.fileItem = item
                FileListItem(context, viewModel, item, navController)
            }
        }
    } else {
        Column {
            Text(text = "No files found.")
        }
    }
}

@Composable
fun FileListItem(
    context: Context,
    viewModel: FileManagerViewModel,
    item: DocumentFile,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (item.isFile) {
                    navController.navigate(Route.FileManager.EDITOR.name)
                }
            }
            .padding(MaterialTheme.spacing.medium)
    ) {
        Icon(
            imageVector = viewModel.getFileIcon(),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        Column {
            Text(item.name!!, color = viewModel.getFileNameColor())
            Text(item.uri.toString(), color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        }
    }
}