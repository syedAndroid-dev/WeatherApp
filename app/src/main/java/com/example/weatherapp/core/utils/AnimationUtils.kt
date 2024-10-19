package com.example.weatherapp.core.utils

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.ui.Alignment

val enterAnimation = expandVertically(
    animationSpec = tween(delayMillis = 300),
    expandFrom = Alignment.Bottom
)

val exitAnimation = shrinkVertically(
    animationSpec = tween(delayMillis = 300),
    shrinkTowards = Alignment.Bottom
)
