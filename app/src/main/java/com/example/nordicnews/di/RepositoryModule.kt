package com.example.nordicnews.di

import com.example.nordicnews.data.repository.ArticleRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {  ArticleRepository(get()) }
}