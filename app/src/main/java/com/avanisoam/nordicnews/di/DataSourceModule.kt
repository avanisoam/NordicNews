package com.avanisoam.nordicnews.di

import com.avanisoam.nordicnews.data.repository.ApiRepository
import org.koin.dsl.module

val remoteDataSourceModule = module {
    factory {  ApiRepository(get()) }
}