package com.example.weatherapp.core.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R


val enterAnimation = expandVertically(
    animationSpec = tween(delayMillis = 300),
    expandFrom = Alignment.Bottom
)

val exitAnimation = shrinkVertically(
    animationSpec = tween(delayMillis = 300),
    shrinkTowards = Alignment.Bottom
)

@Preview
@Composable
private fun CommonLoaderPreview() {
    CommonLoader(isLoading = true)
}

@Composable
fun CommonLoader(
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    val preLoaderLottieAnimation by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.loader
        )
    )
    val preLoaderProgress by animateLottieCompositionAsState(
        preLoaderLottieAnimation,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    AnimatedVisibility(visible = isLoading) {
        Dialog(
            onDismissRequest = {}
        ) {
            LottieAnimation(
                composition = preLoaderLottieAnimation,
                progress = preLoaderProgress,
                modifier = modifier.size(120.dp),
                enableMergePaths = true
            )
        }
    }
}

@Composable
fun WeatherAnimatedImage(
    modifier: Modifier = Modifier,
    weatherType: WeatherType
) {
    val preLoaderLottieAnimation by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            when(weatherType) {
                WeatherType.CLOUD -> R.raw.cloud
                WeatherType.RAIN -> R.raw.rain
                WeatherType.CLEAR -> R.raw.clear
            }
        )
    )
    val preLoaderProgress by animateLottieCompositionAsState(
        preLoaderLottieAnimation,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    LottieAnimation(
        composition = preLoaderLottieAnimation,
        progress = preLoaderProgress,
        modifier = modifier.size(120.dp),
        enableMergePaths = true
    )
}