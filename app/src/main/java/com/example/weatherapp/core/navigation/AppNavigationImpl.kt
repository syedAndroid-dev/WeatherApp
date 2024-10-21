package com.example.weatherapp.core.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class AppNavigationImpl @Inject constructor() : AppNavigation {

    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    override suspend fun navigateBack(route: Destination?, inclusive: Boolean) {
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    override suspend fun navigateTo(
        route: Destination,
        popUpToRoute: Destination?,
        inclusive: Boolean,
        isSingleTop: Boolean,
        saveState: Boolean,
        restoreState: Boolean,
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive,
            )
        )
    }

    override fun tryNavigateTo(
        route: Destination,
        popUpToRoute: Destination?,
        inclusive: Boolean,
        isSingleTop: Boolean,
        saveState: Boolean,
        restoreState: Boolean,
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
                saveState = saveState,
                restoreState = restoreState,
            )
        )
    }
}

