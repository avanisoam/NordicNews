package com.avanisoam.nordicnews.di

import com.avanisoam.nordicnews.NordicNewsApplication
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataStoreModule = module {
    single{
        val application = (androidApplication() as NordicNewsApplication)
        application.userPreferencesRepository
    }
}