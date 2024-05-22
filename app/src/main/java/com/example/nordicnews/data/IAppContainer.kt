package com.example.nordicnews.data

import android.content.Context

interface IAppContainer {
    val articleRepository : ArticleRepository
}

class DefaultAppContainer(private val context: Context) : IAppContainer {

    override val articleRepository: ArticleRepository by lazy {
        ArticleRepository(NordicNewsDatabase.getDatabase(context).articleDao())
    }
}