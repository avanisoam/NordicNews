package com.example.nordicnews.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.data.Constants
import com.example.nordicnews.data.Constants.PROD_BASE_URL
import com.example.nordicnews.data.network.NordicNewsApiService
import com.example.nordicnews.data.repository.ApiRepository
import com.example.nordicnews.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}

fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

fun provideRetrofit(
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

fun provideService(retrofit: Retrofit): NordicNewsApiService =
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