package com.example.nordicnews.data.network

import com.example.nordicnews.data.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
