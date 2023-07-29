package com.bluewhaleyt.common.widget

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnackbar(
    text: String,
    view: View = this.findViewById(android.R.id.content),
    actionText: String? = null,
    action: () -> Unit = {},
) {
    val snackbar = Snackbar.make(this, view, "", Snackbar.LENGTH_SHORT)
    snackbar.setText(text)
    snackbar.setAction(actionText) { action() }
    snackbar.show()
}