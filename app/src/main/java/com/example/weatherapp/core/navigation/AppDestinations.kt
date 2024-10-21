package com.example.weatherapp.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination(val route : String){
    @Serializable
    data object RootModule : Destination("root")

    @Serializable
    data object SplashScreen : Destination("Splash")

    //LoginModule
    @Serializable
    data object LoginScreen: Destination("Login")

    //Dashboard
    @Serializable
    data object HomeScreen : Destination("Home")

    //AddUser
    @Serializable
    data object AddUserScreen : Destination("AddUser")

    //WeatherDetails
    @Serializable
    data object WeatherDetails : Destination("WeatherDetails")
}
