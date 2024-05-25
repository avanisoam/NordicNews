package com.example.nordicnews.data

import com.example.nordicnews.data.models.Article
import kotlinx.coroutines.flow.Flow

interface IArticleRepository {
    /**
     * Insert item in the data source
     */
    suspend fun insertArticle(article: Article)

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getArticle(url: String): Flow<Article?>

    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllArticles(): Flow<List<Article>>

    /**
     * Delete item from the data source
     */
    suspend fun deleteArticle(article: Article)

}