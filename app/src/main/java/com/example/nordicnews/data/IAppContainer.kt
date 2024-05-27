package com.example.nordicnews.data

import android.content.Context
import com.example.nordicnews.data.network.ApiRepository
import com.example.nordicnews.data.network.NordicNewsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface IAppContainer {
    val articleRepository : ArticleRepository
    val apiRepository : ApiRepository
}

class DefaultAppContainer(
    private val context: Context,
    private val baseUrl: String
) : IAppContainer {

    // Production Base Url
    //private val baseUrl = "https://newsapi.org/v2/"  

    // Dev/Local Base Url
    //private val baseUrl = "http://192.168.0.232:5003/api/News/"

/*
    // ToDo : https://stackoverflow.com/questions/34133621/retrofit-400-bad-request
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    clientBuilder.addInterceptor(loggingInterceptor);

 */

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