package com.avanisoam.nordicnews.data.network

import com.avanisoam.nordicnews.data.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

private const val API_KEY = "d6478f2cd0f34abbbe54626f658b33bd"

interface NordicNewsApiService {
    @GET("ping")
    suspend fun ping(): String

    @GET("everything")
    suspend fun getNewsBySource(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("everything?sortBy=popularity")
    suspend fun searchNewsV1(
        @Query("q") query : String,
        @Query("from") from : LocalDate,
        @Query("to") to : LocalDate?,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("everything?sortBy=publishedAt")
    suspend fun searchNewsV2(
        @Query("q") query : String,
        @Query("from") from : LocalDate?,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchNewsByDomain(
        @Query("domains") domains : String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getHeadlinesByCountryAndCategory(
        @Query("country") country: String = Constants.COUNTRY,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("top-headlines?sortBy=publishedAt&pageSize=50")
    suspend fun getHeadlinesByCountryAndCategoryV1(
        @Query("category") category: String,
        @Query("country") country: String = Constants.COUNTRY,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getHeadlinesBySource(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}