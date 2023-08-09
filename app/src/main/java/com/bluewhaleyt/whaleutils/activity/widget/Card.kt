package com.bluewhaleyt.whaleutils.activity.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.bluewhaleyt.common.common.dp
import com.bluewhaleyt.common.common.getLayoutInflater
import com.bluewhaleyt.common.common.toStringResource
import com.bluewhaleyt.whaleutils.R
import com.bluewhaleyt.whaleutils.databinding.WidgetLayoutCardBinding

class Card @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
): CustomLinearLayout(context, attrs, defaultAttrs) {

    var binding: WidgetLayoutCardBinding

    var title: String = ""
        get() = field
        set(value) {
            field = value
            binding.tvCardTitle.text = title
        }

    var subtitle: String = ""
        get() = field
        set(value) {
            field = value
            binding.tvCardSubtitle.text = subtitle
        }

    private var contentPadding = 20.dp

    private var isEmptyEnabled = false
    private var isDividerEnabled = true
    private var isFoldButtonEnabled = false

    var isExpand = true

    init {
        binding = WidgetLayoutCardBinding.inflate(context.getLayoutInflater())
        initAttrs(R.styleable.Card)
        initView()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    private fun initView() {
        addView(binding.root)

        binding.tvCardTitle.text = title
        binding.tvCardSubtitle.text = subtitle

        setPadding(binding.layoutCardAdditionalContent, contentPadding)

        setViewVisible(binding.layoutCardHeader, !isEmptyEnabled)
        setViewVisible2(binding.cardDivider, isDividerEnabled)
        setViewVisible(binding.btnCardFold, isFoldButtonEnabled)

        foldAction()
        binding.btnCardFold.setOnClickListener { foldAction() }
    }

    private fun initAttrs(styleable: IntArray) {
        val ta = obtainAttrs(styleable)
        try {
            title = ta.getString(R.styleable.Card_cardTitle) ?: ""
            subtitle = ta.getString(R.styleable.Card_cardSubtitle) ?: ""
            contentPadding = ta.getDimension(R.styleable.Card_cardContentPadding, contentPadding.toFloat()).toInt()
            isEmptyEnabled = ta.getBoolean(R.styleable.Card_cardEmptyEnabled, isEmptyEnabled)
            isDividerEnabled = ta.getBoolean(R.styleable.Card_cardDividerEnabled, isDividerEnabled)
            isFoldButtonEnabled = ta.getBoolean(R.styleable.Card_cardFoldButtonEnabled, isFoldButtonEnabled)
            isExpand = ta.getBoolean(R.styleable.Card_cardExpand, isExpand)
        } finally {
            ta.recycle()
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (childCount == 1) {
            val layout = when (child?.tag) {
                R.string.tag_card_header.toStringResource(context) -> binding.layoutCardAdditionalHeader
                R.string.tag_card_footer.toStringResource(context) -> binding.layoutCardAdditionalFooter
                else -> binding.layoutCardAdditionalContent
            }
            layout.addView(child, index, params)
        } else {
            super.addView(child, index, params)
        }
    }

    private fun foldAction() {
        if (isExpand) {
            setViewVisible(binding.layoutCardFoldContent, true)
            setViewVisible2(binding.cardDivider, true)
            binding.btnCardFold.rotation = 0f
        } else {
            setViewVisible(binding.layoutCardFoldContent, false)
            setViewVisible2(binding.cardDivider, false)
            binding.btnCardFold.rotation = 180f
        }
        isExpand = !isExpand
    }

}