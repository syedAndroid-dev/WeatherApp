package com.example.weatherapp.di

import com.example.weatherapp.core.navigation.AppNavigation
import com.example.weatherapp.core.navigation.AppNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Singleton
    @Binds
    abstract fun bindAppNavigator(appNavigatorImpl: AppNavigationImpl): AppNavigation
}