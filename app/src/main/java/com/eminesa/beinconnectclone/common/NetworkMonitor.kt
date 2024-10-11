package com.eminesa.beinconnectclone.common

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkMonitor @Inject constructor(
    private val connectivityManager: ConnectivityManager
) {

    fun registerNetworkCallback(onAvailable: () -> Unit, onLost: () -> Unit) {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    onAvailable()
                }

                override fun onLost(network: Network) {
                    onLost()
                }
            })
    }
}
