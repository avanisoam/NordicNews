package com.example.nordicnews.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.data.ArticleRepository
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.ui.detail.DetailViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookmarksViewModel(private  val articleRepository : ArticleRepository) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NordicNewsApplication)
                val articleRepository = application.container.articleRepository
                BookmarksViewModel(articleRepository = articleRepository)
            }
        }
    }

    val uiState: StateFlow<BookmarksUiState> =
        articleRepository.getAllArticles().map { BookmarksUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = BookmarksUiState()
            )
}

data class BookmarksUiState(
    val bookmarks : List<Article> = listOf()
)