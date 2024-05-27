package com.example.nordicnews.dataStore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
){
    private companion object{
        val Is_DebugMode_On = booleanPreferencesKey("is_debugMode_on")
        const val TAG = "UserPreferencesRepo"
    }

    // Write in DataStore
    suspend fun savePreference(isDebugMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[Is_DebugMode_On] = isDebugMode
        }
    }

    // Read from DataStore
    val isDebugMode : Flow<Boolean> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map{ preferences ->
        preferences[Is_DebugMode_On] ?: true
    }
}