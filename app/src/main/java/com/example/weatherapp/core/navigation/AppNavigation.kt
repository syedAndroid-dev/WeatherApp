package com.example.weatherapp.core.navigation

import kotlinx.coroutines.channels.Channel

interface AppNavigation {
    val navigationChannel: Channel<NavigationIntent>

    suspend fun navigateBack(
        route: Destination? = null,
        inclusive: Boolean = false,
    )

    suspend fun navigateTo(
        route: Destination,
        popUpToRoute: Destination? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
        saveState: Boolean = false,
        restoreState: Boolean = false
    )

    fun tryNavigateTo(
        route: Destination,
        popUpToRoute: Destination? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
        saveState: Boolean = false,
        restoreState: Boolean = false
    )
}

sealed class NavigationIntent {

    data class NavigateBack(val route: Destination? = null, val inclusive: Boolean = false) :
        NavigationIntent()

    data class NavigateTo(
        val route: Destination,
        val popUpToRoute: Destination? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
        val saveState: Boolean = false,
        val restoreState: Boolean = false,
    ) : NavigationIntent()
}