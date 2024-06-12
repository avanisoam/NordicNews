package com.avanisoam.nordicnews.ui.detail

import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avanisoam.nordicnews.R
import com.avanisoam.nordicnews.data.repository.ArticleRepository
import com.avanisoam.nordicnews.data.model.Article
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    val savedStateHandle: SavedStateHandle,
    private  val articleRepository : ArticleRepository

) : ViewModel() {

    private val argumentArticleObject = checkNotNull(savedStateHandle.get<Article>("article"))

    val uiState: StateFlow<DetailUiState> =
        articleRepository.getArticle(argumentArticleObject.url).map { article ->
            article?.let { DetailUiState(it) } ?: DetailUiState()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState()
        )

    suspend fun saveItem(article: Article) {
        viewModelScope.launch {
            articleRepository.insertArticle(article)
        }
    }

    suspend fun deleteItem(article: Article) {
        viewModelScope.launch {
            articleRepository.deleteArticle(article)
        }
    }

    fun shareOrder(context: Context) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
        }
        context.startActivity(
            Intent.createChooser(
                intent,
                context.getString(R.string.detail)
            )
        )
    }
}
data class DetailUiState(
    val article : Article = Article()
)

fun Article.toDetailUiState(): DetailUiState = DetailUiState(
    article = this
)