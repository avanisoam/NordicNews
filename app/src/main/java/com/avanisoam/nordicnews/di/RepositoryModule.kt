package com.avanisoam.nordicnews.di

import com.avanisoam.nordicnews.data.repository.ArticleRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {  ArticleRepository(get()) }
}