package com.bluewhaleyt.whaleutils.activity.playground.system

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluewhaleyt.whaleutils.R
import com.bluewhaleyt.whaleutils.activity.ActivityViewHolder
import com.bluewhaleyt.whaleutils.databinding.LayoutListItem1Binding

class SystemInfoAdapter(private val data: List<SystemInfo>) : RecyclerView.Adapter<SystemInfoViewHolder>() {

    private lateinit var binding: LayoutListItem1Binding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemInfoViewHolder {
        binding = LayoutListItem1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SystemInfoViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SystemInfoViewHolder, position: Int) {
        val info = data[position]
        binding.tvText1.text = info.title
        binding.tvText2.apply {
            text = info.value
            setTextIsSelectable(true)
        }

//        binding.tvText1.textSize = if (info.value.isEmpty()) 25f else 14f
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class SystemInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)