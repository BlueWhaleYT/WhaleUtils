package com.bluewhaleyt.sora_editor

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import com.bluewhaleyt.common.common.isInDarkMode
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula
import io.github.rosemoe.sora.widget.schemes.SchemeEclipse
import io.github.rosemoe.sora.widget.schemes.SchemeGitHub
import io.github.rosemoe.sora.widget.schemes.SchemeNotepadXX
import io.github.rosemoe.sora.widget.schemes.SchemeVS2019

class CodeEditor @JvmOverloads constructor(
    private val context: Context,
    private val attrs: AttributeSet? = null
) : io.github.rosemoe.sora.widget.CodeEditor(context, attrs) {

    var cornerRadius: Float = 0f
    var colorScheme: Int = -1
    var text = ""
    var textMateEnabled = false

    init {
        initAttrs(R.styleable.CodeEditor)
        initView()
    }

    private fun initView() {
        apply {
            if (!textMateEnabled) {
                val defaultColorScheme =
                    if (context.isInDarkMode()) SchemeDarcula() else EditorColorScheme()
                val colorScheme: EditorColorScheme = when (this.colorScheme) {
                    0 -> EditorColorScheme()
                    1 -> SchemeDarcula()
                    2 -> SchemeEclipse()
                    3 -> SchemeGitHub()
                    4 -> SchemeNotepadXX()
                    5 -> SchemeVS2019()
                    else -> defaultColorScheme
                }
                setColorScheme(colorScheme)
            }
            setText(text)

            typefaceLineNumber = Typeface.MONOSPACE
            typefaceText = Typeface.MONOSPACE

            setTextSize(14f)

            lineNumberMarginLeft = 30f
            setDividerMargin(30f, 0f)

            setLineSpacing(2f, 1.5f)
        }
    }

    private fun initAttrs(styleable: IntArray) {
        val ta = obtainAttrs(styleable)
        try {
            cornerRadius = ta.getDimension(R.styleable.CodeEditor_editorCornerRadius, cornerRadius)
            colorScheme = ta.getInt(R.styleable.CodeEditor_editorColorScheme, colorScheme)
            text = ta.getString(R.styleable.CodeEditor_editorText) ?: text
            textMateEnabled = ta.getBoolean(R.styleable.CodeEditor_editorTextMateEnabled, textMateEnabled)
        } finally {
            ta.recycle()
        }
    }

    protected open fun obtainAttrs(attrsStyleable: IntArray): TypedArray {
        return context.theme.obtainStyledAttributes(attrs, attrsStyleable, 0, 0)
    }

    fun getEditorCompletion(): EditorAutoCompletion {
        return getComponent(EditorAutoCompletion::class.java)
    }

}