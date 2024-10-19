package com.example.weatherapp.manager.connectivitymanager

sealed class NetworkStatus{
    data object Unknown : NetworkStatus()

    data object Connected : NetworkStatus()

    data object DisConnected : NetworkStatus()

    data object ConnectionGood : NetworkStatus()

    data object ConnectionBad : NetworkStatus()
}