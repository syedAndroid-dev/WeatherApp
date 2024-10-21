package com.example.weatherapp.features.weatherdetails.data.remote.repository

import com.example.weatherapp.core.apiutils.Resource
import com.example.weatherapp.features.weatherdetails.data.remote.model.WeatherDataModel
import com.example.weatherapp.features.weatherdetails.data.remote.service.WeatherApiService
import javax.inject.Inject

class WeatherDetailsRepository @Inject constructor(
    private val weatherApiService: WeatherApiService
) {

    suspend fun getWeatherDetails(): Resource<WeatherDataModel> {
        return weatherApiService.getWeatherDetails(
            queries = mapOf(
                "lat" to "12.9082847623315",
                "lon" to "77.65197822993314",
                "units" to "imperial",
                "appid" to "b143bb707b2ee117e62649b358207d3e",
            )
        )
    }
}