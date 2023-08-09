package com.bluewhaleyt.whaleutils.activity.playground.system

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bluewhaleyt.common.system.DeviceUtils
import com.bluewhaleyt.common.system.SystemUtils
import com.bluewhaleyt.design.widget.recyclerview.RecyclerView
import com.bluewhaleyt.design.widget.recyclerview.adapter.AdapterCallback
import com.bluewhaleyt.design.widget.recyclerview.adapter.CustomAdapter
import com.bluewhaleyt.whaleutils.R
import com.bluewhaleyt.whaleutils.activity.playground.PlaygroundActivity
import com.bluewhaleyt.whaleutils.databinding.ActivityPlaygroundSystemBinding

data class SystemInfo(val name: String, val value: String)

class SystemPlaygroundActivity : PlaygroundActivity() {

    private lateinit var binding: ActivityPlaygroundSystemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaygroundSystemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBinding(binding)
        init()
    }

    private fun init() {
        setupAdapter(binding.rvDisplay, getDisplayList())
        setupAdapter(binding.rvSystem, getSystemList())
    }

    private fun setupAdapter(rv: RecyclerView, list: List<SystemInfo>) {
        val callback = object : AdapterCallback<SystemInfo> {
            override fun onCreateView(itemView: View, data: SystemInfo, itemIndex: Int) {
                val tv1 = itemView.findViewById<TextView>(R.id.tv_text_1)
                val tv2 = itemView.findViewById<TextView>(R.id.tv_text_2)
                tv1.text = data.name
                tv2.apply {
                    text = data.value
                    setTextIsSelectable(true)
                }
            }
        }
        val adapter = CustomAdapter<SystemInfo>(this)
        adapter.setCallback(callback)
            .setLayoutView(R.layout.layout_list_item_1)
            .setClickable(false)
        rv.setAdapter(adapter, list)
    }

    private fun getDisplayList(): List<SystemInfo> {

        val d = DeviceUtils()
        val list = mutableListOf<SystemInfo>()

        add(list, "Model", d.model)
        add(list, "Manufacturer", d.manufacturer)
        add(list, "Product", d.product)
        add(list, "OS Version", d.osVersion)
        add(list, "SDK Version", d.sdkVersion.toString())
        add(list, "SDK Version Code name", d.sdkVersionCodeName)
        add(list, "Runtime Name", d.runtimeName!!)
        add(list, "JVM Name", d.jvmName!!)
        add(list, "JVM Version", d.jvmVersion!!)
        add(list, "Build number", d.buildNumber)
        add(list, "Build fingerprint", d.buildFingerprint)
        add(list, "Bootloader", d.bootLoader)

        return list
    }

    private fun getSystemList(): List<SystemInfo> {

        val s = SystemUtils()
        val list = mutableListOf<SystemInfo>()

        add(list, "Kernel Version", s.kernelVersion!!)
        add(list, "Kernel Arch", s.kernelArchitecture!!)
        add(list, "Core", s.cores.toString())
        add(list, "CPU Processor", s.cpuProcessor.toString())
        add(list, "CPU ABIs", s.cpuSupportedABIs.toList().toString())
        add(list, "CPU Features", s.cpuFeatures!!)

        return list
    }

    private fun add(list: MutableList<SystemInfo>, title: String, value: String) {
        list.add(SystemInfo(title, value))
    }

}