package com.example.weatherapp.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.core.utils.customesnackbar.SnackBarController
import com.example.weatherapp.core.utils.customesnackbar.rememberComposeModifiedSnackBarState
import com.example.weatherapp.core.theme.appDarkBackGround
import com.example.weatherapp.core.theme.appLightBackGround
import com.example.weatherapp.core.utils.customesnackbar.CustomSnackBar
import com.example.weatherapp.core.utils.customesnackbar.SnackBarColor
import com.example.weatherapp.core.utils.customesnackbar.SnackBarDuration
import com.example.weatherapp.core.utils.customesnackbar.SnackBarPosition
import com.example.weatherapp.features.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

@Composable
fun WeatherAppNavigationGraph(
    baseViewModel: BaseViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val mySnackBarState = rememberComposeModifiedSnackBarState()

    ObserveAsEvents(
        flow = baseViewModel.navigationChannel.receiveAsFlow()
    ) { intent ->
        when (intent) {
            is NavigationIntent.NavigateBack -> {
                if (intent.route != null) {
                    navController.popBackStack(intent.route, intent.inclusive)
                } else {
                    navController.popBackStack()
                }
            }

            is NavigationIntent.NavigateTo -> {
                navController.navigate(intent.route) {
                    launchSingleTop = intent.isSingleTop
                    intent.popUpToRoute?.let { popUpToRoute ->
                        popUpTo(popUpToRoute) {
                            inclusive = intent.inclusive
                        }
                    }
                }
            }
        }
    }

    ObserveAsEvents(
        flow = SnackBarController.event
    ) { event ->
        mySnackBarState.showSnackBar(message = event.message)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        NavHost(
            modifier = Modifier
                .weight(1f)
                .background(brush = if (isSystemInDarkTheme()) appDarkBackGround else appLightBackGround),
            navController = navController,
            startDestination = Destination.SplashScreen,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
        ) {
            //SplashScreen
            composable<Destination.SplashScreen> {

            }

            //LoginScreen
            composable<Destination.LoginScreen> {

            }

            //DashboardScreen
            composable<Destination.HomeScreen> {

            }

            //AddUsersScreen
            composable<Destination.AddUserScreen> {

            }

            //WeatherScreen
            composable<Destination.WeatherDetails> {

            }
        }

        CustomSnackBar(
            state = mySnackBarState,
            snackBarPosition = SnackBarPosition.Top,
            containerColor = SnackBarColor.SUCCESS,
            contentColor = SnackBarColor.TextWhite,
            snackBarDuration = SnackBarDuration.SHORT,
            verticalPadding = 12.dp,
            horizontalPadding = 12.dp,
            icon = Icons.Default.Star,
            withDismissAction = true,
        )
    }
}

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2, flow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}