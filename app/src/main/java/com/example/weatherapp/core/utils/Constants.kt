package com.example.weatherapp.core.utils

object Constants {
    var baseUrl: String = "https://api.openweathermap.org"

    const val DB_NAME = "UserDetailsDb.db"
    const val USER_DETAILS = "UserDetails"
    const val USER_ID = "UserId"
    const val USER_NAME = "UserName"
    const val USER_EMAIL = "UserEmail"
}

enum class WeatherType{
    CLOUD,
    RAIN,
    CLEAR
}