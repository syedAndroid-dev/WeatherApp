package com.example.weatherapp.core.utils.customesnackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.weatherapp.core.theme.success
import com.example.weatherapp.core.theme.textWhite
import com.example.weatherapp.core.utils.enterAnimation
import com.example.weatherapp.core.utils.exitAnimation

import java.util.Timer
import kotlin.concurrent.schedule

sealed class SnackBarPosition {
    data object Top : SnackBarPosition()

    data object Bottom : SnackBarPosition()

    data object Float : SnackBarPosition()
}

sealed class SnackBarColor(val color: Color) {
    data object SUCCESS : SnackBarColor(success)
    data object ERROR : SnackBarColor(com.example.weatherapp.core.theme.error)
    data object TextWhite : SnackBarColor(textWhite)
    data class CustomColor(val customColor: Color) : SnackBarColor(customColor)
}

sealed class SnackBarDuration(val time: Long) {
    data object LONG : SnackBarDuration(10000L)
    data object SHORT : SnackBarDuration(3000L)
    data object INFINITE : SnackBarDuration(6000000L)
}

@Composable
internal fun CustomSnackBar(
    state: ModifiedSnackBarState,
    snackBarPosition: SnackBarPosition,
    containerColor: SnackBarColor,
    contentColor: SnackBarColor,
    snackBarDuration: SnackBarDuration,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    icon: ImageVector?,
    withDismissAction: Boolean,
) {
    var showSnackBar by remember { mutableStateOf(false) }
    val infoMessage by rememberUpdatedState(newValue = state.message.value)
    val timer = Timer("Animation Timer", true)

    DisposableEffect(
        key1 = state.updateState
    ) {
        showSnackBar = true
        timer.schedule(snackBarDuration.time) {
            showSnackBar = false
        }

        onDispose {
            timer.cancel()
            timer.purge()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(
                bottom = when (snackBarPosition) {
                    is SnackBarPosition.Top -> 0.dp
                    is SnackBarPosition.Bottom -> 0.dp
                    is SnackBarPosition.Float -> 24.dp
                }
            ),
        verticalArrangement = when (snackBarPosition) {
            is SnackBarPosition.Top -> Arrangement.Top
            is SnackBarPosition.Bottom -> Arrangement.Bottom
            is SnackBarPosition.Float -> Arrangement.Bottom
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            visible = state.isNotEmpty() && showSnackBar,
            enter = when (snackBarPosition) {
                is SnackBarPosition.Top -> enterAnimation
                is SnackBarPosition.Bottom -> enterAnimation
                is SnackBarPosition.Float -> fadeIn()
            },
            exit = when (snackBarPosition) {
                is SnackBarPosition.Top -> exitAnimation
                is SnackBarPosition.Bottom -> exitAnimation
                is SnackBarPosition.Float -> fadeOut()
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(
                        fraction = when (snackBarPosition) {
                            SnackBarPosition.Bottom -> 1f
                            SnackBarPosition.Float -> 0.8f
                            SnackBarPosition.Top -> 1f
                        }
                    )
                    .background(
                        color = containerColor.color,
                        shape = when (snackBarPosition) {
                            is SnackBarPosition.Top -> RectangleShape
                            is SnackBarPosition.Bottom -> RectangleShape
                            is SnackBarPosition.Float -> RoundedCornerShape(8.dp)
                        }
                    )
                    .padding(vertical = verticalPadding, horizontal = horizontalPadding)
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier
                        .weight(4f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    icon?.let {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Snackbar Icon",
                            tint = contentColor.color,
                        )

                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Text(
                        text = infoMessage ?: "unknown message",
                        color = contentColor.color,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                if (withDismissAction) {
                    IconButton(
                        onClick = {
                            showSnackBar = false
                            timer.cancel()
                            timer.purge()
                        },
                        modifier = Modifier
                            .size(25.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Snackbar Dismiss Icon",
                            tint = contentColor.color
                        )
                    }
                }
            }
        }
    }
}