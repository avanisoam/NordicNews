package com.avanisoam.nordicnews.di

import com.avanisoam.nordicnews.NordicNewsApplication
import com.avanisoam.nordicnews.data.Constants
import com.avanisoam.nordicnews.data.Constants.PROD_BASE_URL
import com.avanisoam.nordicnews.data.network.NordicNewsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}
private fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}

private fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

private fun provideRetrofit(
    baseUrl : String,
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

private fun provideService(retrofit: Retrofit): NordicNewsApiService =
    retrofit.create(NordicNewsApiService::class.java)

val networkModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single {
        val application = (androidApplication() as NordicNewsApplication)

        if(application.isDeveloperMode.value)
        {
            provideRetrofit(Constants.DEV_BASE_URL, get(),get())
        }
        else
        {
            provideRetrofit(PROD_BASE_URL, get(),get())
        }
    }
    single { provideService(get()) }
}