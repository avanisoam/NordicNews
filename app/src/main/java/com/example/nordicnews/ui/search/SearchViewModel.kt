package com.example.nordicnews.ui.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.network.ApiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    savedStateHandle: SavedStateHandle,
    private  val apiRepository: ApiRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[
                    ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY
                ] as NordicNewsApplication
                val apiRepository = application.container.apiRepository
                val savedStateHandle = this.createSavedStateHandle()
                SearchViewModel(
                    savedStateHandle = savedStateHandle,
                    apiRepository = apiRepository
                )
            }
        }
    }

    private val category: String? = savedStateHandle[
        SearchDestination.CATEGORY_ARG
    ]

    var uiState = MutableStateFlow(SearchUiState())
        private set

    fun onNameChange(value : String) {
        uiState.update { currentUiState ->
            currentUiState.copy(
                name = value
            )
        }
    }
    private fun getFilteredNews(
        sources : String = "ars-technica"
    ) {
        viewModelScope.launch {
            try {
                uiState.update { currentUiState ->
                    currentUiState.copy(
                        searchApiState = SearchApiState.LOADING
                    )
                }
                delay(3000)  // the delay of 3 seconds

                val allNews = apiRepository.getHeadlinesByCountryAndCategoryV1(sources)

                uiState.update { currentUiState ->
                    currentUiState.copy(
                        articleList = allNews.articles,
                        searchApiState = SearchApiState.SUCCESS
                    )
                }
            } catch (_ : Exception) {
                uiState.update { currentUiState ->
                    currentUiState.copy(
                        searchApiState = SearchApiState.ERROR
                    )
                }
            }
        }
    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        uiState.update { currentUiState ->
            currentUiState.copy(
                searchWidgetState = newValue
            )
        }
    }

    fun searchNews(keyword : String) {
        viewModelScope.launch {
            try {
                uiState.update { currentUiState ->
                    currentUiState.copy(
                        searchApiState = SearchApiState.LOADING
                    )
                }
                val allNews = apiRepository.searchNewsV2(keyword)
                Log.d("AllNews", "${allNews.totalResults} no.of news")
                uiState.update { currentUiState ->
                    currentUiState.copy(
                        articleList = allNews.articles,
                        searchApiState = SearchApiState.SUCCESS
                    )
                }
            } catch (_ : Exception){
                uiState.update { currentUiState ->
                    currentUiState.copy(
                        searchApiState = SearchApiState.ERROR
                    )
                }
            }
        }
    }

    init {
        if (category != null) {
            uiState.update { currentUiState ->
                currentUiState.copy(
                    category = category,
                )
            }
            getFilteredNews(uiState.value.category.lowercase())
        }
    }
}
enum class SearchWidgetState {
    OPENED,
    CLOSED
}
enum class SearchApiState {
    SUCCESS,
    ERROR,
    LOADING,
    NONE
}
data class SearchUiState(
    val searchWidgetState : SearchWidgetState = SearchWidgetState.OPENED,
    val name : String = "",
    val articleList : List<Article> = listOf(),
    val category : String = "Everything",
    val searchApiState : SearchApiState = SearchApiState.NONE,
)