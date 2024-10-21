package com.example.weatherapp.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val DarkColorScheme = darkColorScheme(
    background = DarkBlue20,
    onBackground = Color.White,
    primary = DarkBlue99,
    onPrimary = Color.White,
    primaryContainer = DarkBlue10,
    onPrimaryContainer = Color.White,
    surface = DarkBlue10,
    onSurface = DarkBlue99,
    surfaceVariant = DarkBlue10,
    secondary = DarkBlue10,
    onSecondary = DarkBlue99,
    secondaryContainer = greyNatural,
    onSecondaryContainer = textWhite,
    tertiary = easternBlue,
    onTertiary = Color.White,
    onTertiaryContainer = Color.Black
)

val LightColorScheme = lightColorScheme(
    background = DarkBlue96,
    onBackground = DarkBlue90,
    primary = DarkBlue30,
    onPrimary = Color.White,
    primaryContainer = DarkBlue80,
    onPrimaryContainer = DarkBlue10,
    surface = Color.White,
    onSurface = Grey20,
    surfaceVariant = DarkBlue30,
    secondary = DarkBlue30,
    onSecondary = Color.White,
    secondaryContainer = Color.White,
    onSecondaryContainer = textBlack,
    tertiary = darkBlue,
    onTertiary = Color.Black,
    onTertiaryContainer = Color.White
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}