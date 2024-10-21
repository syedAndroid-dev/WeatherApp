package com.example.weatherapp.features

import androidx.lifecycle.ViewModel
import com.example.weatherapp.manager.datastoremanager.DataStorePreferenceManager
import com.example.weatherapp.core.navigation.AppNavigation
import com.example.weatherapp.core.navigation.Destination
import com.example.weatherapp.core.navigation.NavigationIntent
import com.example.weatherapp.manager.datastoremanager.DataStoreManagerImpl.DataStoreKeys.KEY_IS_LOGGED_IN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor():ViewModel() {

    @Inject lateinit var appNavigator: AppNavigation

    @Inject lateinit var dataStorePreference: DataStorePreferenceManager

    val navigationChannel: Channel<NavigationIntent> get() = appNavigator.navigationChannel

    suspend fun onNavigate(
        route: Destination,
        popUpToRoute: Destination? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false
    ) {
        navigationChannel.send(
            element = NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop
            )
        )
    }

    suspend fun getStartDestination(): Destination {
        return if (dataStorePreference.getPreference(key = KEY_IS_LOGGED_IN, false).first()) Destination.HomeScreen else Destination.LoginScreen
    }
}