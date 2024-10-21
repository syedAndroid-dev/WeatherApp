package com.example.weatherapp.features.addusers.presentation

sealed class AddUserEvents {

    data class OnUserFirstNameEntered(
        val firstName: String
    ) : AddUserEvents()

    data class OnUserSecondNameEntered(
        val secondName: String
    ) : AddUserEvents()

    data class OnEmailEntered(
        val email: String
    ) : AddUserEvents()

    data object OnAddUserDetails : AddUserEvents()

    data object OnCancel : AddUserEvents()

    data object OnBackPressed : AddUserEvents()
}