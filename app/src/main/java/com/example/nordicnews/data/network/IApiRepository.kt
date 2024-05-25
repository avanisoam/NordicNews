package com.example.nordicnews.data.network

interface IApiRepository {
    suspend fun getNews(sources : String,page : Int) : NewsResponse
    suspend fun getArticlesByCategoryAsync(category : String) : NewsResponse

}

class ApiRepository(private val nordicNewsApiService: NordicNewsApiService) : IApiRepository{

    override suspend fun getNews(sources: String, page: Int): NewsResponse {
        return  nordicNewsApiService.getNews(sources,page)
    }

    override suspend fun getArticlesByCategoryAsync(category: String): NewsResponse {
        return nordicNewsApiService.getArticlesByCategoryAsync(category)
    }

}