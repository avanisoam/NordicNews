package com.example.nordicnews

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.nordicnews.data.DefaultAppContainer
import com.example.nordicnews.data.IAppContainer
import com.example.nordicnews.dataStore.UserPreferencesRepository

private const val PREFERENCE_NAME = "nordicnews_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCE_NAME
)
class NordicNewsApplication : Application() {
    lateinit var container: IAppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        // container = DefaultAppContainer()
        container = DefaultAppContainer(this)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }

}