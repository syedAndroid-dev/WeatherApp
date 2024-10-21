package com.example.weatherapp.features.weatherdetails.presentation

sealed class WeatherDetailsEvents {

    data object OnBackPressed : WeatherDetailsEvents()

    data object OnLogOutClicked : WeatherDetailsEvents()

}