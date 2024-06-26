package com.avanisoam.nordicnews.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avanisoam.nordicnews.data.repository.ArticleRepository
import com.avanisoam.nordicnews.data.model.Article
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookmarksViewModel(private  val articleRepository : ArticleRepository) : ViewModel() {

    val uiState: StateFlow<BookmarksUiState> =
        articleRepository.getAllArticles().map {
            BookmarksUiState(it.reversed())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BookmarksUiState()
        )
}

data class BookmarksUiState(
    val bookmarks : List<Article> = listOf()
)