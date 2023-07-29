package com.bluewhaleyt.common.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import java.io.PrintWriter
import java.io.StringWriter

fun Context.getAttributeColor(resourceName: String, alpha: Float = 1f): Int {
    val resourceId = resources.getIdentifier(resourceName, "attr", packageName)
    val typedValue = TypedValue()
    theme.resolveAttribute(resourceId, typedValue, true)
    val color = typedValue.data
    return ColorUtils.setAlphaComponent(color, (alpha * 255).toInt())
}

fun Context.getResourceFont(resId: Int): Typeface {
    return ResourcesCompat.getFont(this, resId)!!
}

fun String.setSpan(color: Int, textStyle: Int = Typeface.NORMAL): SpannableString {
    val spannable = SpannableString(this)
    spannable.setSpan(ForegroundColorSpan(color), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable.setSpan(StyleSpan(textStyle), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}

fun Int.toStringResource(context: Context): String {
    return context.resources.getString(this)
}

fun Int.toColorResource(context: Context): Int {
    return ContextCompat.getColor(context, this)
}

fun Context.waitFor(delayMillis: Long, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        action()
    }, delayMillis)
}

fun Context.getLayoutInflater(): LayoutInflater {
    return LayoutInflater.from(this)
}

fun Array<StackTraceElement>.string(): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    this.forEach { pw.println(it) }
    return sw.toString()
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()