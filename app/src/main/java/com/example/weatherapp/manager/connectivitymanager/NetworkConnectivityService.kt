package com.example.weatherapp.manager.connectivitymanager

import kotlinx.coroutines.flow.Flow


interface NetworkConnectivityService {
    val networkStatus : Flow<NetworkStatus>
}