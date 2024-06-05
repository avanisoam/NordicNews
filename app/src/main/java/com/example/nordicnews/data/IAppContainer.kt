package com.example.nordicnews.data

import android.content.Context
import com.example.nordicnews.data.database.NordicNewsDatabase
import com.example.nordicnews.data.repository.ApiRepository
import com.example.nordicnews.data.network.NordicNewsApiService
import com.example.nordicnews.data.repository.ArticleRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface IAppContainer {
    val articleRepository : ArticleRepository
    val apiRepository : ApiRepository
}

class DefaultAppContainer(
    private val context: Context,
    baseUrl: String
) : IAppContainer {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : NordicNewsApiService by lazy {
        retrofit.create(NordicNewsApiService::class.java)
    }

    override val apiRepository: ApiRepository by lazy {
        ApiRepository(retrofitService)
    }

    override val articleRepository: ArticleRepository by lazy {
        ArticleRepository(NordicNewsDatabase.getDatabase(context).articleDao())
    }
}