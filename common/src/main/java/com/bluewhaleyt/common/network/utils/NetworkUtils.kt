package com.bluewhaleyt.common.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkUtils {
    companion object {

        fun isNetworkConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkRequest = NetworkRequest.Builder().build()
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {}
                override fun onLost(network: Network) {}
            }
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            val isNetworkAvailable =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            connectivityManager.unregisterNetworkCallback(networkCallback)
            return isNetworkAvailable ?: false
        }
    }
}