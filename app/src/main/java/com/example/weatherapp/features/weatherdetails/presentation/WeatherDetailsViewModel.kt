package com.example.weatherapp.features.weatherdetails.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.apiutils.Resource
import com.example.weatherapp.core.navigation.Destination
import com.example.weatherapp.core.utils.WeatherType
import com.example.weatherapp.core.utils.customesnackbar.SnackBarController
import com.example.weatherapp.core.utils.customesnackbar.SnackBarEvent
import com.example.weatherapp.core.utils.getFormattedSunriseTime
import com.example.weatherapp.core.utils.valueOrDefault
import com.example.weatherapp.features.BaseViewModel
import com.example.weatherapp.features.weatherdetails.data.remote.repository.WeatherDetailsRepository
import com.example.weatherapp.manager.datastoremanager.DataStoreManagerImpl.DataStoreKeys.KEY_IS_LOGGED_IN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val weatherDetailsRepository: WeatherDetailsRepository
) : BaseViewModel() {

    private val _weatherDetailsState = MutableStateFlow(WeatherDetailsState())
    val weatherDetailsState = _weatherDetailsState.asStateFlow()

    init {
        getWeatherDetails()
    }

    fun onEvent(weatherDetailsEvents: WeatherDetailsEvents) {
        when (weatherDetailsEvents) {
            WeatherDetailsEvents.OnBackPressed -> {
                viewModelScope.launch {
                    appNavigator.navigateBack()
                }
            }

            WeatherDetailsEvents.OnLogOutClicked -> {
                viewModelScope.launch{
                    appNavigator.tryNavigateTo(
                        route = Destination.LoginScreen,
                        popUpToRoute = Destination.LoginScreen,
                        inclusive = true
                    )
                    SnackBarController.sendEvent(event = SnackBarEvent(message = "LogOut SuccessFully.."))
                    dataStorePreference.putPreference(key = KEY_IS_LOGGED_IN,false)
                }

            }
        }
    }

    private fun getWeatherDetails() {
        viewModelScope.launch {
            _weatherDetailsState.update { it.copy(isLoading = true)}
            val weatherDetails = weatherDetailsRepository.getWeatherDetails()
            when (weatherDetails) {
                is Resource.Success -> {
                    val data = weatherDetails.data
                    Log.e("weatherdetails","$data")
                    _weatherDetailsState.update {
                        it.copy(
                            isLoading = false,
                            weatherLocation = "Bengaluru",
                            weatherDescription = weatherDetails.data?.current?.weather?.first()?.description.valueOrDefault(),
                            currentTemp = "${weatherDetails.data?.current?.temp}",
                            lastUpDateTime = "",
                            humidity = "${weatherDetails.data?.current?.humidity}",
                            windSpeed = "${weatherDetails.data?.current?.windSpeed}",
                            sunset = getFormattedSunriseTime(timestamp = weatherDetails.data?.current?.sunset ?: 0L),
                            sunrise = getFormattedSunriseTime(timestamp =weatherDetails.data?.current?.sunrise ?: 0L)
                        )
                    }
                }

                is Resource.Error -> {
                    SnackBarController.sendEvent(event = SnackBarEvent(message = "SomeThing Went Wrong"))
                }
            }
            _weatherDetailsState.update { it.copy(isLoading = false)}
        }
    }
}

data class WeatherDetailsState(
    val isLoading: Boolean = false,
    val weatherLocation: String = "-",
    val weatherDescription: String = "-",
    val currentTemp: String = "-",
    val lastUpDateTime: String = "-",
    val humidity: String = "-",
    val windSpeed: String = "-",
    val weatherType: WeatherType = WeatherType.RAIN,
    val sunset: String = "-",
    val sunrise: String = "-",
)