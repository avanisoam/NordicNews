package com.example.nordicnews

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.nordicnews.data.Constants
import com.example.nordicnews.data.DefaultAppContainer
import com.example.nordicnews.data.IAppContainer
import com.example.nordicnews.dataStore.UserPreferencesRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

private const val PREFERENCE_NAME = "nordicnews_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCE_NAME
)
class NordicNewsApplication : Application() {
    lateinit var container: IAppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository

    private val isDevloperMode = mutableStateOf(false)

    private suspend fun readDataStoreSync() {
        val dataStoreKey = booleanPreferencesKey("is_debugMode_on")
        val preferences = dataStore.data.first()

        isDevloperMode.value =  preferences[dataStoreKey]  ?: false

        Log.d("OnLoad:Blocking/Sync", "${isDevloperMode.value}")
    }

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)

        Log.d("OnLoad:Before", "${isDevloperMode.value}")

        // https://stackoverflow.com/questions/73414839/how-to-load-instantly-data-from-data-store
        // https://developer.android.com/topic/libraries/architecture/datastore#synchronous
        //GlobalScope.launch {
            val exampleData = runBlocking {
                //context.dataStore.data.first()
                readDataStoreSync()
            }
        //}

        Log.d("OnLoad:After", "${isDevloperMode.value}")

        /*
        val userName: StateFlow<String> = userPreferencesRepository.userName()
            .filter {
                it.isNotEmpty()
            }
            .stateIn(
                GlobalScope,
                SharingStarted.WhileSubscribed(),
                "qwerty"
            )

        //Log.d("OnLoad", "${userPreferencesRepository.currentUserName}")
        Log.d("OnLoad", "${userName.value}")
         */

        // container = DefaultAppContainer()

        container = if(isDevloperMode.value) {
            DefaultAppContainer(
                this,
                Constants.DEV_BASE_URL
            )
        } else {
            DefaultAppContainer(
                this,
                Constants.PROD_BASE_URL
            )
        }

    }

}