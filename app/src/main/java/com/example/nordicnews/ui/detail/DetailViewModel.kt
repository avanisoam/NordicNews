package com.example.nordicnews.ui.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.data.ArticleRepository
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.models.AssetParamType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private  val articleRepository : ArticleRepository

) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NordicNewsApplication)
                val articleRepository = application.container.articleRepository
                val savedStateHandle = this.createSavedStateHandle()
                DetailViewModel(savedStateHandle, articleRepository = articleRepository)
            }
        }
    }

    /*
    var uiState by mutableStateOf(DetailUiSate())
        private set

     */
    //var uiState = MutableStateFlow(DetailUiSate())
      //  private set

    private val argumentArticleObject = checkNotNull(savedStateHandle.get<Article>("article"))

    val uiState: StateFlow<DetailUiState> =
        articleRepository.getArticle(argumentArticleObject.url)
            //.filterNotNull()
            .map { article ->
                if(article == null) {
                    DetailUiState()
                }
                else{
                    DetailUiState(article = article)
                }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DetailUiState()
            )

    /*
    init {

        Log.d("argument", argument.url)
        viewModelScope.launch {
            uiState.value = articleRepository.getArticle(argument.url)
                .filterNotNull()
                .first()
                .toDetailUiState()
        }
    }

     */

    /**
     * Holds current item ui state
     */
    // Backing property to avoid state updates from other classes
    // private val _uiState = MutableStateFlow(DetailUiSate())
    // val uiState: StateFlow<DetailUiSate> = _uiState.asStateFlow()

    /*
    var uiState = MutableStateFlow(DetailUiSate())
        private set

     */

    suspend fun saveItem(article : Article) {
        viewModelScope.launch {
            articleRepository.insertArticle(article)
        }

        /*
        uiState.update {currentUiState ->
            currentUiState.copy(
                isBookmarked = !(uiState.value.isBookmarked),
                article = article
            )
        }
        if(uiState.value.isBookmarked)
        {
            articleRepository.insertArticle(article)
        }
        else {

            // Delete Api
        }
         */
    }

    suspend fun deleteItem(article: Article){
        viewModelScope.launch {
            articleRepository.deleteArticle(article)
        }
    }
}
data class DetailUiState(
    //val isBookmarked : Boolean = false,
    val article : Article = Article()
)

fun Article.toDetailUiState(): DetailUiState = DetailUiState(
    //isBookmarked = isBookmarked,
    article = this
)