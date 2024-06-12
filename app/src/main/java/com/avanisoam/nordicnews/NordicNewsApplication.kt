package com.avanisoam.nordicnews

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.avanisoam.nordicnews.data.Constants
import com.avanisoam.nordicnews.dataStore.UserPreferencesRepository
import com.avanisoam.nordicnews.di.dataBaseModule
import com.avanisoam.nordicnews.di.dataStoreModule
import com.avanisoam.nordicnews.di.networkModule
import com.avanisoam.nordicnews.di.remoteDataSourceModule
import com.avanisoam.nordicnews.di.repositoryModule
import com.avanisoam.nordicnews.di.viewModelModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFERENCE_NAME
)
class NordicNewsApplication : Application() {

    lateinit var userPreferencesRepository: UserPreferencesRepository

    val isDeveloperMode = mutableStateOf(false)

    private suspend fun readDataStoreSync() {
        val dataStoreKey = booleanPreferencesKey(Constants.DEBUG_MODE_PREFERENCE)
        val preferences = dataStore.data.first()

        isDeveloperMode.value =  preferences[dataStoreKey]  ?: false

        Log.d("OnLoad:", " In runBlocking DeveloperMode is ${isDeveloperMode.value}")
    }

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)

        Log.d("OnLoad:", " Before reading DeveloperMode is ${isDeveloperMode.value}")

        // https://stackoverflow.com/questions/73414839/how-to-load-instantly-data-from-data-store
        // https://developer.android.com/topic/libraries/architecture/datastore#synchronous
        runBlocking {
            readDataStoreSync()
        }

        Log.d("OnLoad:", " After reading DeveloperMode is ${isDeveloperMode.value}")

        startKoin{
            androidContext(this@NordicNewsApplication)

            modules(
                dataBaseModule,
                remoteDataSourceModule,
                networkModule,
                repositoryModule,
                viewModelModule,
                dataStoreModule
            )
        }

    }

}