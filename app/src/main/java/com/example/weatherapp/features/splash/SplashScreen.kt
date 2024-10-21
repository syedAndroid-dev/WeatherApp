package com.example.weatherapp.features.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    val preLoaderLottieAnimation by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.splash_weather
        )
    )
    val preLoaderProgress by animateLottieCompositionAsState(
        preLoaderLottieAnimation,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)),
        ){
            LottieAnimation(
                composition = preLoaderLottieAnimation,
                progress = preLoaderProgress,
                modifier = modifier.size(120.dp),
                enableMergePaths = true
            )
        }
    }
}