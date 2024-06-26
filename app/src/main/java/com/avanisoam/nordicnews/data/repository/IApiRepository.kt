package com.avanisoam.nordicnews.data.repository

import android.util.Log
import com.avanisoam.nordicnews.data.network.NewsResponse
import com.avanisoam.nordicnews.data.network.NordicNewsApiService
import java.time.LocalDate

interface IApiRepository {

    suspend fun ping(): String

    suspend fun getNewsBySource(
        sources: String = "bbc-news",
        page: Int = 1
    ): NewsResponse

    suspend fun searchNewsV1(
        keyword: String = "apple",
        from: LocalDate,
        to: LocalDate? = LocalDate.now()
    ): NewsResponse

    suspend fun searchNewsV2(
        keyword: String = "tesla",
        from: LocalDate? = LocalDate.now().minusMonths(1)
    ): NewsResponse
    suspend fun searchNewsByDomain(
        keyword: String = "wsj.com"
    ): NewsResponse
    suspend fun getHeadlinesByCountryAndCategory(
        country: String = "us",
        category: String = "business"
    ): NewsResponse

    suspend fun getHeadlinesByCountryAndCategoryV1(
        category: String
    ): NewsResponse

    suspend fun getHeadlinesBySource(
        source: String = "techcrunch"
    ): NewsResponse

}

class ApiRepository(
    private val nordicNewsApiService: NordicNewsApiService
): IApiRepository {
    override suspend fun ping(): String = nordicNewsApiService.ping()

    override suspend fun getNewsBySource(sources: String, page: Int): NewsResponse =
       nordicNewsApiService.getNewsBySource(sources,page)

    override suspend fun searchNewsV1(
        keyword: String,
        from: LocalDate,
        to: LocalDate?
    ): NewsResponse = nordicNewsApiService.searchNewsV1(keyword, from, to)

    override suspend fun searchNewsV2(keyword: String, from: LocalDate?): NewsResponse {
        Log.d("Search_Request_Params", "$keyword : $from")
        return nordicNewsApiService.searchNewsV2(keyword,from)
    }

    override suspend fun searchNewsByDomain(keyword: String): NewsResponse {
        return nordicNewsApiService.searchNewsByDomain(keyword)
    }

    override suspend fun getHeadlinesByCountryAndCategory(
        country: String,
        category: String
    ): NewsResponse = nordicNewsApiService.getHeadlinesByCountryAndCategory(
        country,
        category
    )
    override suspend fun getHeadlinesByCountryAndCategoryV1(
        category: String
    ): NewsResponse = nordicNewsApiService.getHeadlinesByCountryAndCategoryV1(
        category
    )

    override suspend fun getHeadlinesBySource(source: String): NewsResponse {
       return  nordicNewsApiService.getHeadlinesBySource(source)
    }
}