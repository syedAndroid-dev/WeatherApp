package com.example.weatherapp.manager.connectivitymanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectivityManager @Inject constructor(
    context: Context
) : NetworkConnectivityService {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val activeNetwork = connectivityManager.activeNetwork

    @RequiresApi(Build.VERSION_CODES.N)
    override val networkStatus: Flow<NetworkStatus> = callbackFlow {

        val connectivityCallback = object : NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch {
                    send(NetworkStatus.Connected)
                }
            }

            override fun onUnavailable() {
                super.onUnavailable()
                launch {
                    send(NetworkStatus.DisConnected)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch {
                    send(NetworkStatus.DisConnected)
                }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                launch {
                    send(NetworkStatus.ConnectionBad)
                }
            }

//            override fun onCapabilitiesChanged(
//                network: Network,
//                networkCapabilities: NetworkCapabilities
//            ) {
//                super.onCapabilitiesChanged(network, networkCapabilities)
//                val unMetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
//
//                if(unMetered){
//                    launch {
//                        send(NetworkStatus.ConnectionBad)
//
//                    }
//                } else {
//                    launch {
//                        send(NetworkStatus.ConnectionGood)
//
//                    }
//                }
////                if(activeNetwork != null){
////                   // val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
////                    if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
////                        val linkDownStreamBandWithKbps = networkCapabilities.linkDownstreamBandwidthKbps
////                        Log.e("networkspeedwifi","$linkDownStreamBandWithKbps")
////                        if(linkDownStreamBandWithKbps >= 5000){
////                            launch {
////                                send(NetworkStatus.ConnectionGood)
////                            }
////                        } else {
////                            launch {
////                                send(NetworkStatus.ConnectionBad)
////                            }
////                        }
////                    } else if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
////                        val linkDownStreamBandWithKbps = networkCapabilities.linkDownstreamBandwidthKbps
////                        Log.e("networkspeedcellular","$linkDownStreamBandWithKbps")
////                        if(linkDownStreamBandWithKbps >= 2000){
////                            launch {
////                                send(NetworkStatus.ConnectionGood)
////                            }
////                        } else {
////                            launch {
////                                send(NetworkStatus.ConnectionBad)
////                            }
////                        }
////                    }
////                }
//            }

        }

        connectivityManager.registerDefaultNetworkCallback( connectivityCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(connectivityCallback)
        }

    }.distinctUntilChanged().flowOn(Dispatchers.IO)
}