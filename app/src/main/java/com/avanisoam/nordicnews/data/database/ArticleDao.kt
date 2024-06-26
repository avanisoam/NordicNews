package com.avanisoam.nordicnews.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avanisoam.nordicnews.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * from bookmarks WHERE url = :url")
    fun getItem(url: String): Flow<Article?>

    @Query("SELECT * from bookmarks")
    fun getAllItems(): Flow<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}