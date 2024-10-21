package com.example.weatherapp.core.utils

fun String?.valueOrDefault(default: String = ""): String = if (this !== null) this else default

fun String?.isValidEmail() = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$").matches(this.valueOrDefault())

fun String.isValidPassword() = this.length >= 8