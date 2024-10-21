package com.example.weatherapp.features.splash

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.navigation.Destination
import com.example.weatherapp.features.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor():BaseViewModel() {

    init {
        viewModelScope.launch{
            delay(1000)
            navigateToLoginScreen()
        }
    }

    private suspend fun navigateToLoginScreen() {
        onNavigate(
            route = getStartDestination(),
            popUpToRoute = Destination.SplashScreen,
            inclusive = true
        )
    }
}