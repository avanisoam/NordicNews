package com.avanisoam.nordicnews.data

object Constants {
    val API_KEY: String
        get() = "YOUR_API_KEY_HERE"
    val COUNTRY: String
        get() = "us"

    val PROD_BASE_URL: String
        get() = "https://newsapi.org/v2/"

    // To enable developer's mode only
    val DEV_BASE_URL: String
        get() = "http://YOUR_MACHINE_IP_HERE:PORT/api/News/"

    val PREFERENCE_NAME : String = "nordicnews_preferences"

    val DEBUG_MODE_PREFERENCE : String = "is_debugMode_on"
    val LITE_MODE_PREFERENCE : String = "is_lightmode_on"
}