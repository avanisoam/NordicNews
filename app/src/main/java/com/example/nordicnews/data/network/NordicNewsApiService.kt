package com.example.nordicnews.data.network

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "d6478f2cd0f34abbbe54626f658b33bd"

interface NordicNewsApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY //API_KEY
    ): NewsResponse
}