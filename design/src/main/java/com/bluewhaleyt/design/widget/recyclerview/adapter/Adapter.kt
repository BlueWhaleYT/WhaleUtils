package com.bluewhaleyt.design.widget.recyclerview.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

interface Adapter<T> {

    fun setLayoutView(resId: Int): CustomAdapter<T>

//    fun setLayoutManager(orientation: Int, reverseLayout: Boolean = false): CustomAdapter<T>

    fun setLayoutManager(layoutManager: LayoutManager): CustomAdapter<T>
    fun setFilterable(filterable: Boolean): CustomAdapter<T>

    fun setClickable(clickable: Boolean): CustomAdapter<T>

    fun setCallback(adapterCallback: AdapterCallback<T>): CustomAdapter<T>

    fun addViewType(viewType: Any, layoutResId: Int): CustomAdapter<T>

    fun addData(items: List<T>): CustomAdapter<T>

    fun updateData(item: T): CustomAdapter<T>

    fun build(recyclerView: RecyclerView): CustomAdapter<T>

}