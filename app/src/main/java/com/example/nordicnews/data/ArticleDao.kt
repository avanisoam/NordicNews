package com.example.nordicnews.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nordicnews.data.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * from bookmarks WHERE url = :url")
    fun getItem(url: String): Flow<Article?>

    @Query("SELECT * from bookmarks")
    fun getAllItems(): Flow<List<Article>>
}