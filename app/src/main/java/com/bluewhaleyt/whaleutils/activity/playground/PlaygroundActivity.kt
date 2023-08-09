package com.bluewhaleyt.whaleutils.activity.playground

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bluewhaleyt.common.common.catchException
import com.bluewhaleyt.common.common.string
import com.bluewhaleyt.common.coroutines.runInBackground
import com.bluewhaleyt.common.coroutines.runOnUI
import com.bluewhaleyt.common.widget.showSnackbar
import com.bluewhaleyt.whaleutils.PromptManager
import com.bluewhaleyt.whaleutils.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

open class PlaygroundActivity : AppCompatActivity() {

    private lateinit var binding: ViewBinding
    private lateinit var dialog: AlertDialog

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_playground, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val promptManager = PromptManager(this, binding)
        when (item.itemId) {
            R.id.menu_how_to_use -> promptManager.set()
        }
        return super.onOptionsItemSelected(item)
    }

    fun setBinding(binding: ViewBinding) {
        this.binding = binding
    }

    fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun showDialog(
        title: String,
        message: String
    ) {
        dialog = MaterialAlertDialogBuilder(this).create()
        dialog.apply {
            setTitle(title)
            setMessage(message)
            show()
        }
    }

    fun dismissDialog() {
        dialog.dismiss()
    }

    fun catchExceptionWithSnackbar(tag: String = this.javaClass.simpleName, code: suspend () -> Unit) {
        runInBackground {
            catchException(
                catchAction = { e ->
                    Log.e(tag, e.message, e)
                    runOnUI {
                        showSnackbar(
                            text = e.message.toString(),
                            actionText = "inspect"
                        ) {
                            showDialog(
                                title = "inspect",
                                message = e.stackTrace.string()
                            )
                        }
                    }
                }
            ) { code() }
        }
    }

}