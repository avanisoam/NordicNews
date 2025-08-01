package com.avanisoam.nordicnews.data.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avanisoam.nordicnews.data.network.NordicNewsApiService
import kotlinx.coroutines.delay

class NewsPagingSource(
    private val newsApiService: NordicNewsApiService,
): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val response = newsApiService.getNews(page = page)

            //delay(3000L)  // For testing purpose only

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.articles.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}