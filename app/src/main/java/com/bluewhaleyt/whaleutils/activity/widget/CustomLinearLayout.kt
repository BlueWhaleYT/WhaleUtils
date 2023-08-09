package com.bluewhaleyt.whaleutils.activity.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import com.bluewhaleyt.common.common.dp
import com.bluewhaleyt.whaleutils.R


open class CustomLinearLayout @JvmOverloads constructor(
    private val context: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
): LinearLayout(context, attrs, defaultAttrs) {

    var isDefaultMarginEnabled = true

    init {
        this.initAttrs(R.styleable.CustomLinearLayout)
    }

    private fun initAttrs(styleable: IntArray) {
        val ta = obtainAttrs(styleable)
        try {
            isDefaultMarginEnabled = ta.getBoolean(R.styleable.CustomLinearLayout_linearLayoutDefaultMarginEnabled, isDefaultMarginEnabled)
        } finally {
            ta.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isDefaultMarginEnabled) applyDefaultMargin()
    }

    private fun applyDefaultMargin() {
        val lp = layoutParams as MarginLayoutParams
        val margin = 15.dp
        val margin2 = margin / 6.dp
//        lp.marginStart = margin
//        lp.marginEnd = margin

        if (isFirstChild()){
            lp.topMargin = margin
            lp.bottomMargin = margin / margin2
        }
        if (isMiddleChild()){
             lp.topMargin = margin / margin2
             lp.bottomMargin = margin / margin2
        }
        if (isLastChild()){
            lp.topMargin = margin / margin2
            lp.bottomMargin = margin
        }

        layoutParams = lp
    }

    protected open fun obtainAttrs(attrsStyleable: IntArray): TypedArray {
        return context.theme.obtainStyledAttributes(attrs, attrsStyleable, 0, 0)
    }

    protected open fun setViewVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected open fun setViewVisible2(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    protected open fun setPadding(view: View, padding: Int) {
        view.setPadding(padding, padding, padding, padding)
    }

    protected open fun isFirstChild(): Boolean {
        val parent = parent as ViewGroup
        return parent.getChildAt(0) == this
    }

    protected open fun isLastChild(): Boolean {
        val parent = parent as ViewGroup
        return parent.getChildAt(parent.childCount - 2) == this
    }

    protected open fun isMiddleChild(): Boolean {
        val parent = parent as? ViewGroup ?: return false
        val index = parent.indexOfChild(this)
        return index > 0 && index < parent.childCount - 1
    }

    private fun getPrevView(): View? {
        val parent = parent as ViewGroup
        val index = parent.indexOfChild(this)
        return if (index > 0) parent.getChildAt(index - 1) else null
    }

    private fun getNextView(): View? {
        val parent = parent as ViewGroup
        val index = parent.indexOfChild(this)
        return if (index < parent.childCount - 1) parent.getChildAt(index + 1) else null
    }


    class OnViewGlobalLayoutListener(private val view: View, height: Int) :
        ViewTreeObserver.OnGlobalLayoutListener {
        private var maxHeight = 500

        init {
            maxHeight = height
        }

        override fun onGlobalLayout() {
            if (view.height > maxHeight) view.layoutParams.height = maxHeight
        }
    }


}