package com.bluewhaleyt.whaleutils.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluewhaleyt.common.storage.SharedPrefsUtils
import com.bluewhaleyt.whaleutils.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val packageManager = packageManager
        val activities = packageManager
            .getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            .activities.filter { !it.exported }

        binding.rvActivity.layoutManager = LinearLayoutManager(this)
        binding.rvActivity.adapter = ActivityAdapter(activities.toList())
        welcomeToast()

    }

    private fun welcomeToast() {
        val prefs = SharedPrefsUtils(this, "prefs_main")
        val key = "shown"
        if (!prefs.isOneTime(key)){
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
            prefs.write(key, true, true)
        }
    }


}