package com.bryanguerra.listmessagesportfolio.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define el DataStore globalmente, sin necesidad de mantener el context en una propiedad
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {

    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")

    fun accessToken(context: Context): Flow<String?> {
        return context.applicationContext.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
    }

    suspend fun saveAccessToken(context: Context, token: String) {
        context.applicationContext.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun clearAccessToken(context: Context) {
        context.applicationContext.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
        }
    }
}