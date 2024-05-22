package com.example.nordicnews

import android.app.Application
import com.example.nordicnews.data.DefaultAppContainer
import com.example.nordicnews.data.IAppContainer

class NordicNewsApplication : Application() {
    lateinit var container: IAppContainer

    override fun onCreate() {
        super.onCreate()
        // container = DefaultAppContainer()
        container = DefaultAppContainer(this)
    }

}