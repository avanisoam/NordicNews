package com.example.nordicnews.data.network

import com.example.nordicnews.data.Constants
import retrofit2.Response
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

    @GET("top-headlines?sortBy=publishedAt&pageSize=50")
    suspend fun getArticlesByCategoryAsync(
        @Query("category") category: String,
        @Query("country") country: String = Constants.COUNTRY,
        @Query("apiKey") apiKey: String = API_KEY //API_KEY
    ): NewsResponse
}