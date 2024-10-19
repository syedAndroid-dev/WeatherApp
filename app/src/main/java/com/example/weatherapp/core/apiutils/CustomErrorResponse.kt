package com.example.weatherapp.core.apiutils

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CustomErrorResponse(
    val message: String,
    val exception: String,
    val exc_type: String,
    val exc: String
)
