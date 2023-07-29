package com.bluewhaleyt.whaleutils.activity.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.bluewhaleyt.whaleutils.R

class TextButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0,
) : AppCompatTextView(ContextThemeWrapper(context, R.style.WhaleUtils_TextButton), attrs, defaultAttrs) {

}