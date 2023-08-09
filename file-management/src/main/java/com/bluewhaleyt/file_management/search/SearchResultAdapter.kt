package com.bluewhaleyt.file_management.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluewhaleyt.materialfileicon.core.FileIconHelper

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    private var searchResults: List<SearchResult>? = null

    var viewHolder: ViewHolder? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        clickListener = listener
    }

    fun setSearchResults(searchResults: List<SearchResult>) {
        this.searchResults = searchResults
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(com.bluewhaleyt.res.R.layout.layout_search_result_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.viewHolder = holder
        val context = holder.itemView.context
        val result = searchResults!![position]
        holder.itemView.setOnClickListener { v: View? ->
            if (clickListener != null) {
                clickListener!!.onItemClick(v, result)
            }
        }
        holder.tvFilePath.text = result.filePath
        holder.tvResult.text = result.highlightedContent
        holder.tvLineNumber.text = "Line: ${result.lineNumber}"
        val fileIconHelper = FileIconHelper(result.filePath)
        fileIconHelper.isDynamicFolderEnabled = true
        fileIconHelper.isEnvironmentEnabled = true
        fileIconHelper.bindIcon(holder.imgFileIcon)
    }

    override fun getItemCount(): Int {
        return searchResults?.size ?: 0
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFilePath: TextView
        val tvLineNumber: TextView
        val tvResult: TextView
        val imgFileIcon: ImageView

        init {
            tvFilePath = view.findViewById(com.bluewhaleyt.res.R.id.tv_file_path)
            tvLineNumber = view.findViewById(com.bluewhaleyt.res.R.id.tv_line_number)
            tvResult = view.findViewById(com.bluewhaleyt.res.R.id.tv_result)
            imgFileIcon = view.findViewById(com.bluewhaleyt.res.R.id.img_file_icon)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, result: SearchResult?)
    }

    companion object {
        private var clickListener: OnItemClickListener? = null
    }
}