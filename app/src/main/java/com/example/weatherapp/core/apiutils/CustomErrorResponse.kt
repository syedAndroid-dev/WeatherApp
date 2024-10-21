package com.example.weatherapp.core.apiutils

import androidx.annotation.Keep

@Keep
data class CustomErrorResponse(
    val message: String,
    val exception: String,
    val excType: String,
    val exc: String
)