package com.example.weatherapp.features.auth.presentation

sealed class LoginEvents {
    data class OnEmailIdChanged(
        val email : String
    ) : LoginEvents()

    data class OnPasswordChanged(
        val password : String
    ) : LoginEvents()

    data class OnSubmitClicked(
        val userId : String = "",
        val password: String = ""
    ) : LoginEvents()
}