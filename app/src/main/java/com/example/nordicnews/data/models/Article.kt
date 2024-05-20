package com.example.nordicnews.data.models

import androidx.annotation.DrawableRes

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
   //@DrawableRes
    //val urlToImage: Int
)
