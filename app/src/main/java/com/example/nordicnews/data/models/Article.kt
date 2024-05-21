package com.example.nordicnews.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: Source = Source(null,""),
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
    //@DrawableRes
    //val urlToImage: Int
) : Parcelable