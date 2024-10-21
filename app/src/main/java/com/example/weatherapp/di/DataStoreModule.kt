package com.example.weatherapp.di

import com.example.weatherapp.manager.datastoremanager.DataStoreManagerImpl
import com.example.weatherapp.manager.datastoremanager.DataStorePreferenceManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Singleton
    @Binds
    abstract fun bindDataStoreManager(
        dataStoreManager: DataStoreManagerImpl,
    ) : DataStorePreferenceManager
}