package com.avanisoam.nordicnews.data.network

import com.avanisoam.nordicnews.data.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
