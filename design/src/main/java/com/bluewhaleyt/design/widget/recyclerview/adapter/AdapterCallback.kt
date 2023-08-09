package com.bluewhaleyt.design.widget.recyclerview.adapter

import android.view.View

interface AdapterCallback<T> {
    fun onCreateView(itemView: View, data: T, itemIndex: Int)

    fun onItemClick(itemView: View, data: T, itemIndex: Int) {}

    fun onItemLongClick(itemView: View, data: T, itemIndex: Int) {}

    fun getItemViewType(data: T, itemIndex: Int): Int = itemIndex
}