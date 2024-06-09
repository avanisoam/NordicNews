package com.example.nordicnews.di

import com.example.nordicnews.data.repository.ApiRepository
import org.koin.dsl.module

val remoteDataSourceModule = module {
    factory {  ApiRepository(get()) }
}