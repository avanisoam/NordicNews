package com.example.nordicnews.data

import com.example.nordicnews.data.models.Article
import kotlinx.coroutines.flow.Flow

class ArticleRepository(private val articleDao: ArticleDao) : IArticleRepository {

    override suspend fun insertArticle(article: Article) = articleDao.insert(article)
    override fun getArticle(url: String): Flow<Article?> = articleDao.getItem(url)
    override fun getAllArticles(): Flow<List<Article>> = articleDao.getAllItems()
    override suspend fun deleteArticle(article: Article) {
        articleDao.delete(article)
    }
}