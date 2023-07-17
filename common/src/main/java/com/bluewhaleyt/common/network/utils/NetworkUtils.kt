package com.bluewhaleyt.common.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.net.NetworkSpecifier
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build

class NetworkUtils(context: Context) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val network = connectivityManager.activeNetwork
    private val capabilities = connectivityManager.getNetworkCapabilities(network)

    fun isConnected(): Boolean {
        return capabilities!!.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun isConnectedWithWiFi(): Boolean {
        return capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    fun isConnectedWithMobile(): Boolean {
        return capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}