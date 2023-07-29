package com.bluewhaleyt.whaleutils.activity.playground.system

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluewhaleyt.common.system.DeviceUtils
import com.bluewhaleyt.common.system.SystemUtils
import com.bluewhaleyt.whaleutils.activity.playground.PlaygroundActivity
import com.bluewhaleyt.whaleutils.databinding.ActivityPlaygroundSystemBinding

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
        val adapterDisplay = SystemInfoAdapter(getDisplayList())
        val adapterSystem = SystemInfoAdapter(getSystemList())

        binding.rvDisplay.adapter = adapterDisplay
        binding.rvSystem.adapter = adapterSystem
        binding.rvDisplay.layoutManager = LinearLayoutManager(this)
        binding.rvSystem.layoutManager = LinearLayoutManager(this)
    }

    fun getDisplayList(): List<SystemInfo> {

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

    fun getSystemList(): List<SystemInfo> {

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