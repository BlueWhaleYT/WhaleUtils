package com.bluewhaleyt.common.common

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Locale
import kotlin.reflect.KProperty1

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

fun String.highlightText(query: String, color: Int): SpannableString {
    val spannableString = SpannableString(this)
    val startIndex = this.lowercase(Locale.getDefault())
        .indexOf(query.lowercase(Locale.getDefault()))
    if (startIndex != -1) {
        spannableString.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            startIndex + query.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannableString
}

fun catchException(
    catchAction: suspend (e: Exception) -> Unit,
    tryAction: suspend () -> Unit,
) {
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    scope.launch {
        try {
            tryAction()
        } catch (e: Exception) {
            catchAction(e)
        }
    }
}

fun catchException(
    tag: String = Thread.currentThread().stackTrace[2].className,
    tryAction: suspend () -> Unit,
) {
    catchException(
        tryAction = tryAction,
        catchAction = { Log.e(tag, it.message, it) }
    )
}

fun Class<*>.isKotlinClass(): Boolean {
    return this.declaredAnnotations.any {
        it.annotationClass.qualifiedName == "kotlin.Metadata"
    }
}

fun Context.isInDarkMode(): Boolean{
    val nightModeFlags: Int =
        this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    when (nightModeFlags) {
        Configuration.UI_MODE_NIGHT_YES -> return true
        Configuration.UI_MODE_NIGHT_NO -> return false
    }
    return true
}

fun String.getFileContentFromAssets(context: Context): String {
    val inputStream = context.assets.open(this)
    val buffer = ByteArray(inputStream.available())
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer)
}

fun <T> T.getPropertyValue(propertyName: String): Any? {
    val property = this!!::class.members.find { it.name == propertyName } as? KProperty1<Any, *>
    return property?.get(this)?.toString()
}

fun Number.toPx(context: Context): Number {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.px: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()