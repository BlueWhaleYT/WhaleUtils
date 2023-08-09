package com.bluewhaleyt.design.widget.recyclerview.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluewhaleyt.common.common.getLayoutInflater
import com.bluewhaleyt.common.common.getPropertyValue
import java.util.Locale
import kotlin.properties.Delegates

class CustomAdapter<T>(
    private var context: Context,
) : RecyclerView.Adapter<CustomAdapter<T>.ViewHolder>(), Adapter<T>, Filterable {

    var list = mutableListOf<T>()
    var filteredList = mutableListOf<T>()
    private val viewTypes = mutableMapOf<Int, Int>()

    private var isFilterable: Boolean = false
    private var isClickable: Boolean = true

    private var layoutResId by Delegates.notNull<Int>()
    private var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
    private lateinit var adapterCallback: AdapterCallback<T>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return if (isFilterable) filteredList.size
        else list.size
    }

    override fun getItemViewType(position: Int): Int {
        val data = if (isFilterable) filteredList[position] else list[position]
        return adapterCallback.getItemViewType(data, position)
    }

    override fun getItemId(position: Int): Long {
        return if (isFilterable) filteredList[position].toString().toLong()
        else list[position].toString().toLong()
    }

    override fun addViewType(viewType: Any, layoutResId: Int): CustomAdapter<T> {
        viewTypes[viewType as Int] = layoutResId
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutResId = viewTypes[viewType] ?: this.layoutResId
        val itemView = parent.context.getLayoutInflater().inflate(layoutResId, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = if (isFilterable) filteredList[position] else list[position]

        adapterCallback.onCreateView(holder.itemView, data, position)
        holder.itemView.apply {
            if (this@CustomAdapter.isClickable) {
                setOnClickListener { adapterCallback.onItemClick(it, data, position) }
                setOnLongClickListener { adapterCallback.onItemLongClick(it, data, position); true }
            }
        }
    }

    override fun setLayoutView(resId: Int): CustomAdapter<T> {
        this.layoutResId = resId
        return this
    }

    override fun setLayoutManager(layoutManager: RecyclerView.LayoutManager): CustomAdapter<T> {
        this.layoutManager = layoutManager
        return this
    }

    override fun setFilterable(filterable: Boolean): CustomAdapter<T> {
        this.isFilterable = filterable
        return this
    }

    override fun setCallback(adapterCallback: AdapterCallback<T>): CustomAdapter<T> {
        this.adapterCallback = adapterCallback
        return this
    }

    override fun setClickable(clickable: Boolean): CustomAdapter<T> {
        this.isClickable = clickable
        return this
    }

    override fun addData(items: List<T>): CustomAdapter<T> {
        list = items as MutableList<T>
        filteredList = list
        notifyDataSetChanged()
        return this
    }

    fun updateData(items: List<T>): CustomAdapter<T> {
        list.clear()
        list.addAll(items)
        filteredList = list
        notifyDataSetChanged()
        return this
    }

    override fun updateData(item: T): CustomAdapter<T> {
        if (!list.contains(item)) list.add(item)
        else {
            val index = list.indexOf(item)
            list[index] = item
        }
        notifyDataSetChanged()
        return this
    }

    override fun build(recyclerView: RecyclerView): CustomAdapter<T> {
        recyclerView.apply {
            this.adapter = this@CustomAdapter
            this.layoutManager = this@CustomAdapter.layoutManager
        }
        return this
    }

//    override fun getFilter(): Filter {
//        return getFilter1()
//    }

//    private fun getFilter(func: (T, String?) -> Boolean): Filter {
//        return object : Filter() {
//            override fun performFiltering(p0: CharSequence?): FilterResults {
//                val query = p0?.toString()?.lowercase(Locale.ROOT)
//                val resultList = if (!query.isNullOrEmpty()) {
//                    list.filter { func(it, query) }.toMutableList()
//                } else {
//                    list.toMutableList()
//                }
//                filteredList = resultList
//
//                val filterResults = FilterResults()
//                filterResults.values = filteredList
//
//                return filterResults
//            }
//
//            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
//                filteredList = p1?.values as MutableList<T>
//                notifyDataSetChanged()
//            }
//        }
//    }
//
//    private fun getFilter1(): Filter {
//        return getFilter { row, query ->
//            val rowStr = row.toString().lowercase(Locale.ROOT)
//            rowStr.contains(query ?: "")
//        }
//    }
//
//    private fun getFilter2(vararg propertyNames: String): Filter {
//        return getFilter { row, query ->
//            val rowStr = propertyNames
//                .map { getPropertyValue(row, it)?.toString()?.lowercase(Locale.ROOT) }
//                .joinToString(" ")
//            rowStr.contains(query ?: "", ignoreCase = true)
//        }
//    }
//
//    fun filter(queryText: CharSequence, vararg queryFields: String) {
//        if (queryFields.isEmpty()) {
//            getFilter1().filter(queryText)
//        } else {
//            getFilter2(*queryFields).filter(queryText)
//        }
//    }
//
//    private fun getPropertyValue(obj: T, propertyName: String): Any? {
//        val property = obj!!::class.members.find { it.name == propertyName } as? KProperty1<Any, *>
//        return property?.get(obj)?.toString()
//    }

    override fun getFilter(): Filter {
        return simpleFilter()
    }

    private fun getFilter(func: (T, String?) -> Boolean): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val query = p0?.toString()?.lowercase(Locale.ROOT)
                val resultList = if (!query.isNullOrEmpty()) {
                    list.filter { func(it, query) }.toMutableList()
                } else {
                    list.toMutableList()
                }
                filteredList = resultList

                val filterResults = FilterResults()
                filterResults.values = filteredList

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredList = p1?.values as MutableList<T>
                notifyDataSetChanged()
            }
        }
    }

    fun addFilter(filterMode: FilterMode, queryText: CharSequence, vararg params: Any) {
        when (filterMode) {
            FilterMode.SIMPLE -> {
                val stringParams = params.map { it.toString() }.toTypedArray()
                if (params.isEmpty()) simpleFilter().filter(queryText)
                else simpleFilter2(*stringParams).filter(queryText)
            }
        }
    }

    private fun simpleFilter(): Filter {
        return getFilter { row, query ->
            val rowStr = row.toString().lowercase(Locale.ROOT)
            rowStr.contains(query ?: "")
        }
    }

    private fun simpleFilter2(vararg propertyNames: String): Filter {
        return getFilter { row, query ->
            val rowStr = propertyNames
                .map { row.getPropertyValue(it)?.toString()?.lowercase(Locale.ROOT) }
                .joinToString(" ")
            rowStr.contains(query ?: "", ignoreCase = true)
        }
    }

}

enum class FilterMode {
    SIMPLE
}