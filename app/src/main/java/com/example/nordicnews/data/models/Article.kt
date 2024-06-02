package com.example.nordicnews.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "bookmarks")
@Parcelize
data class Article(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: Source = Source(null,""),
    val title: String = "",
    @PrimaryKey
    val url: String = "",
    val urlToImage: String = ""
) : Parcelable