package com.bluewhaleyt.whaleutils.activity.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatTextView
import com.bluewhaleyt.whaleutils.R

class Divider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0,
) : View(ContextThemeWrapper(context, R.style.WhaleUtils_Divider), attrs, defaultAttrs) {

}