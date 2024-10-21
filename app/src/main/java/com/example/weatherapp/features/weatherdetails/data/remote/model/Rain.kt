package com.example.weatherapp.features.weatherdetails.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rain(
    @SerialName("1h") val oneHour: Double
)