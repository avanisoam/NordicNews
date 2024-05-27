package com.example.nordicnews.data

object Constants {
    val API_KEY: String
        get() = "d6478f2cd0f34abbbe54626f658b33bd"//BuildConfig.API_KEY
    val COUNTRY: String
        get() = "in"

    val PROD_BASE_URL : String
        get() = "https://newsapi.org/v2/"

    val DEV_BASE_URL : String
        get() = "http://192.168.0.232:5003/api/News/"
}