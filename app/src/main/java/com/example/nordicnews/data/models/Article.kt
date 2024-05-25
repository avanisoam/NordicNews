package com.example.nordicnews.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "bookmarks")
@Parcelize
data class Article(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    // TODO: Handle null
    //val description: String? = "",
    val publishedAt: String = "",
    val source: Source = Source(null,""),
    val title: String = "",
    @PrimaryKey
    val url: String = "",
    //val url: String? = null,
    val urlToImage: String = "",
    // TODO: Handle null
    //val urlToImage: String? = "",
    //@DrawableRes
    //val urlToImage: Int
) : Parcelable