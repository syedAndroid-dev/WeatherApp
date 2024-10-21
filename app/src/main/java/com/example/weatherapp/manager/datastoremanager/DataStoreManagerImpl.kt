package com.example.weatherapp.manager.datastoremanager

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStorePreferenceManager {

    companion object DataStoreKeys {
        val KEY_IS_LOGGED_IN = booleanPreferencesKey("key.is.logged.in")
    }

    private val preferenceName = "weatherApp"
    private val Context.dataStore by preferencesDataStore(name = preferenceName)

    override fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences->
            preferences[key] = value
        }
    }

    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    override suspend fun clearAllPreferences() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}