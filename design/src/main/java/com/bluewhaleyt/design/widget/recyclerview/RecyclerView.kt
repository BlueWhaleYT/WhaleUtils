package com.bluewhaleyt.design.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.bluewhaleyt.design.widget.recyclerview.adapter.CustomAdapter

class RecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): RecyclerView(context, attrs) {

    var adapter: CustomAdapter<*>? = null

    fun <T> setAdapter(adapter: CustomAdapter<T>, data: List<T>?) {
        this.adapter = adapter
        adapter.apply {
            data?.let { addData(it) }
            build(this@RecyclerView)
        }
    }

}