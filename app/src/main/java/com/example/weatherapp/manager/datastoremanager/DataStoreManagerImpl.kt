package com.example.weatherapp.manager.datastoremanager

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
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
        val CURRENT_USER_ID = stringPreferencesKey("key.currentUserId")
        val KEY_THEME = stringPreferencesKey("key.theme")
        val KEY_BASE_URL = stringPreferencesKey("key.base.url")
        val KEY_ACCESS_TOKEN = stringPreferencesKey("key.access.token")
        val KEY_SID_COOKIE = stringPreferencesKey("key.sid.cookie")
        val KEY_SID_EXPIRY = stringPreferencesKey("key.sid.expiry")
        val KEY_IS_LOGGED_IN = booleanPreferencesKey("key.is.logged.in")
        val KEY_USER_DESCRIPTOR = stringPreferencesKey("key.is.user.descriptor")
    }

    private val preferenceName = "DigiLearn"
    private val Context.dataStore by preferencesDataStore(name = preferenceName)

    override fun <T> getPreference(key: androidx.datastore.preferences.core.Preferences.Key<T>, defaultValue: T): Flow<T> {
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