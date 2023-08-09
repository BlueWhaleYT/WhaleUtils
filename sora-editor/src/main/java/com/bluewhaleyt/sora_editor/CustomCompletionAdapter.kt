package com.bluewhaleyt.sora_editor

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bluewhaleyt.common.common.getLayoutInflater
import com.bluewhaleyt.common.common.px
import io.github.rosemoe.sora.widget.component.EditorCompletionAdapter

class CustomCompletionAdapter : EditorCompletionAdapter() {

    private var itemHeight: Int = 45

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?, isCurrentCursorPosition: Boolean): View {
        val view = parent!!.context.getLayoutInflater().inflate(R.layout.layout_completion_list_item, parent, false) ?: null
        val item = getItem(position)
        val holder = ViewHolder(view!!)

        holder.tvCompletionLabel.apply {
            text = item.label
        }

        holder.tvCompletionDesc.apply {
            text = item.desc
        }

        holder.imgCompletionIcon.apply {
            setImageDrawable(item.icon)
        }

        return view

    }

    override fun getItemHeight(): Int {
        return itemHeight.px
    }

    fun setItemHeight(itemHeight: Int) = apply {
        this.itemHeight = itemHeight
    }
    private class ViewHolder(itemView: View) {
        val tvCompletionLabel = itemView.findViewById<TextView>(R.id.tv_completion_label)
        val tvCompletionDesc = itemView.findViewById<TextView>(R.id.tv_completion_desc)
        val tvCompletionId = itemView.findViewById<TextView>(R.id.tv_completion_id)
        val imgCompletionIcon = itemView.findViewById<ImageView>(R.id.img_completion_icon)
    }

}