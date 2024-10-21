package com.example.weatherapp.features.home.presentation


sealed class HomeEvents {
    data object OnGetUsers : HomeEvents()

    data object OnAddUsers : HomeEvents()

    data class OnRemoveUser(
        val userId : Int
    ) : HomeEvents()

    data object OnGetWeatherDetails : HomeEvents()
}