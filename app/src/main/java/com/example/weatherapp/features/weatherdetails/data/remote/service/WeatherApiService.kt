package com.example.weatherapp.features.weatherdetails.data.remote.service

import com.example.weatherapp.core.apiutils.BaseApiClient
import com.example.weatherapp.core.apiutils.BaseApiService
import com.example.weatherapp.core.apiutils.EndPoints.WEATHER
import com.example.weatherapp.core.apiutils.Resource
import com.example.weatherapp.features.weatherdetails.data.remote.model.WeatherDataModel
import javax.inject.Inject

class WeatherApiService @Inject constructor(
    private val baseApiClient: BaseApiClient
):BaseApiService() {

    suspend fun getWeatherDetails(queries : Map<String,String>) : Resource<WeatherDataModel>{
        return safeApiCall {
            baseApiClient.callGetRequest(
                endPoint = WEATHER,
                queryParams = queries
            )
        }
    }
}