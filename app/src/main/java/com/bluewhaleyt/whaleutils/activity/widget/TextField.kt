package com.bluewhaleyt.whaleutils.activity.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatEditText
import com.bluewhaleyt.whaleutils.R

class TextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0,
) : AppCompatEditText(ContextThemeWrapper(context, R.style.WhaleUtils_TextField), attrs, defaultAttrs) {

}