package com.example.weatherapp.features.home.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.navigation.Destination
import com.example.weatherapp.core.utils.customesnackbar.SnackBarController
import com.example.weatherapp.core.utils.customesnackbar.SnackBarEvent
import com.example.weatherapp.features.BaseViewModel
import com.example.weatherapp.features.home.data.local.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState(isLoading = true))
    val homeScreenState = _homeScreenState.asStateFlow()

    init {
        getSavedUsers()
    }

    fun onEvent(homeEvents: HomeEvents) {
        when (homeEvents) {
            HomeEvents.OnAddUsers -> {
                onAddUsers()
            }

            HomeEvents.OnGetUsers -> {
                onGetUsers()
            }

            HomeEvents.OnGetWeatherDetails -> {
                onGetWeatherDetails()
            }

            is HomeEvents.OnRemoveUser -> {
                onRemoveUser(userId = homeEvents.userId)
            }
        }
    }

    private fun getSavedUsers() {
        viewModelScope.launch {
            _homeScreenState.update { it.copy(isLoading = false) }
            homeRepository.getUserDetails().collectLatest { user ->
                Log.e("saved users", "${user}")
                _homeScreenState.update {
                    it.copy(users = user.map { mappedUser ->
                        UserDetails(
                            userId = mappedUser.userId ?: 0,
                            userName = mappedUser.userName,
                            userEmail = mappedUser.userEmail
                        )
                    })
                }
            }
        }
    }

    private fun onRemoveUser(userId: Int) {
        viewModelScope.launch {
            homeRepository.delete(
                userId = userId
            )
            SnackBarController.sendEvent(event = SnackBarEvent(message = "User Removed.."))
        }
    }

    private fun onAddUsers() {
        viewModelScope.launch {
            appNavigator.tryNavigateTo(
                route = Destination.AddUserScreen,
            )
        }
    }

    private fun onGetUsers() {

    }

    private fun onGetWeatherDetails() {
        viewModelScope.launch {
            appNavigator.tryNavigateTo(
                route = Destination.WeatherDetails,
            )
        }
    }
}

data class HomeScreenState(
    val isLoading: Boolean,
    val users: List<UserDetails> = arrayListOf()
)

data class UserDetails(
    val userId: Int,
    val userName: String,
    val userEmail: String,
)