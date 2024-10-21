package com.example.weatherapp.core.utils

import android.os.Build
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String?.valueOrDefault(default: String = ""): String = if (this !== null) this else default

fun String?.isValidEmail() = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$").matches(this.valueOrDefault())

fun String.isValidPassword() = this.length >= 6


fun getFormattedSunriseTime(timestamp: Long, zoneId: String = "UTC"): String {
    val instant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochSecond(timestamp)
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        .withZone(ZoneId.of(zoneId))
    return formatter.format(instant)
}

