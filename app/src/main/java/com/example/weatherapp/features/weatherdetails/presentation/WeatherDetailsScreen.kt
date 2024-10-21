package com.example.weatherapp.features.weatherdetails.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.core.utils.CommonLoader
import com.example.weatherapp.core.utils.WeatherAnimatedImage
import com.example.weatherapp.core.utils.WeatherType


@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    WeatherScreen(
        isLoading = true,
        weatherLocation = "Bengaluru",
        weatherType = WeatherType.CLOUD,
        lastUpDateTime = "17/10/2024",
        humidity = "80%",
        windSpeed = "96k/h",
        currentTemp = "96",
        weatherDescription = "drizzle",
        sunset = "1:00",
        sunrise = "1:00",
        onEvents = {}
    )
}

@Composable
fun WeatherScreenRoute(
    modifier: Modifier = Modifier,
    weatherDetailsViewModel: WeatherDetailsViewModel = hiltViewModel()
) {
    val weatherDetailsState by weatherDetailsViewModel.weatherDetailsState.collectAsStateWithLifecycle()

    WeatherScreen(
        isLoading = weatherDetailsState.isLoading,
        weatherLocation = weatherDetailsState.weatherLocation,
        weatherDescription = weatherDetailsState.weatherDescription,
        currentTemp = weatherDetailsState.currentTemp,
        lastUpDateTime = weatherDetailsState.lastUpDateTime,
        humidity = weatherDetailsState.humidity,
        windSpeed = weatherDetailsState.windSpeed,
        sunset = weatherDetailsState.sunset,
        sunrise = weatherDetailsState.sunrise,
        weatherType = weatherDetailsState.weatherType,
        onEvents = weatherDetailsViewModel::onEvent
    )
}

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    isLoading : Boolean,
    weatherLocation: String,
    weatherDescription: String,
    currentTemp: String,
    lastUpDateTime: String,
    humidity: String,
    windSpeed: String,
    sunset: String,
    sunrise: String,
    weatherType: WeatherType,
    onEvents : (WeatherDetailsEvents) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        CommonLoader(
            isLoading = isLoading
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    onEvents(WeatherDetailsEvents.OnBackPressed)
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = stringResource(R.string.str_weather_details),
                fontSize = 18.sp
            )
            IconButton(
                onClick = {
                    onEvents(WeatherDetailsEvents.OnLogOutClicked)
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout"
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherAnimatedImage(
                weatherType = weatherType
            )
            Text(
                text = currentTemp,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(modifier = modifier.padding(6.dp))
            Text(
                text = weatherLocation,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = modifier.padding(6.dp))
            Text(
                text = weatherDescription,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraLight
            )
            Spacer(modifier = modifier.padding(3.dp))
            Text(
                text = lastUpDateTime,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraLight
            )
        }

        Card(
            modifier = modifier.padding(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                10.dp
            )
        ) {
            Column {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_humidity),
                            contentDescription = "humidity",
                            modifier = modifier.size(30.dp)
                        )
                        Column(
                            modifier = modifier.padding(10.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.str_humidity),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraLight
                            )
                            Spacer(modifier = modifier.padding(3.dp))
                            Text(
                                text = humidity,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_wind),
                            contentDescription = "windspeed",
                            modifier = modifier.size(30.dp)
                        )
                        Column(
                            modifier = modifier.padding(10.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.str_wind_speed),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraLight
                            )
                            Spacer(modifier = modifier.padding(3.dp))
                            Text(
                                text = windSpeed,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = .5f)
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.sunrise),
                            contentDescription = "humidity",
                            modifier = modifier.size(30.dp)
                        )
                        Column(
                            modifier = modifier.padding(10.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.str_sunrise),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraLight
                            )
                            Spacer(modifier = modifier.padding(3.dp))
                            Text(
                                text = sunrise,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_sunset),
                            contentDescription = "windspeed",
                            modifier = modifier.size(30.dp)
                        )
                        Column(
                            modifier = modifier.padding(10.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.str_sunset),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraLight
                            )
                            Spacer(modifier = modifier.padding(3.dp))
                            Text(
                                text = sunset,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}