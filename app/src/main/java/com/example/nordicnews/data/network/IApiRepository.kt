package com.example.nordicnews.data.network

interface IApiRepository {
    suspend fun getNews(sources : String,page : Int) : NewsResponse
}

class ApiRepository(private val nordicNewsApiService: NordicNewsApiService) : IApiRepository{

    override suspend fun getNews(sources: String, page: Int): NewsResponse {
        return  nordicNewsApiService.getNews(sources,page)
    }

}