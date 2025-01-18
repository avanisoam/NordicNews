package com.avanisoam.nordicnews.ui.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avanisoam.nordicnews.data.model.Article
import com.avanisoam.nordicnews.data.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    savedStateHandle: SavedStateHandle,
    private  val apiRepository: ApiRepository
) : ViewModel() {

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
                if(allNews.articles.isNotEmpty()) {
                    uiState.update { currentUiState ->
                        currentUiState.copy(
                            articleList = allNews.articles,
                            searchApiState = SearchApiState.SUCCESS
                        )
                    }
                }
                else{
                    uiState.update { currentUiState ->
                        currentUiState.copy(
                            searchApiState = SearchApiState.NONE
                        )
                    }
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

    fun onCrossButtonClick(newValue: SearchWidgetState){
        viewModelScope.launch {
            try {
                uiState.update { currentUiState ->
                    currentUiState.copy(
                        searchWidgetState = newValue,
                        name = ""
                    )
                }
                val allNews = apiRepository.getNewsBySource("the-verge")
                Log.d("AllNews", "${allNews.totalResults} no.of news")
                uiState.update { currentUiState ->
                    currentUiState.copy(
                        articleList = allNews.articles,
                        searchApiState = SearchApiState.SUCCESS
                    )
                }
            }catch (_ : Exception){
                uiState.update { currentUiState ->
                    currentUiState.copy(
                        searchApiState = SearchApiState.ERROR
                    )
                }
            }

        }
    }

    fun searchNews(keyword : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState.update { currentUiState ->
                    currentUiState.copy(
                        searchApiState = SearchApiState.LOADING
                    )
                }
                val allNews = apiRepository.searchNewsV2(keyword)
                Log.d("AllNews1", "${allNews.totalResults} no.of news")
                //Log.d("AllNews2", "$allNews")
                if(allNews.articles.isNotEmpty()) {
                    uiState.update { currentUiState ->
                        currentUiState.copy(
                            articleList = allNews.articles,
                            searchApiState = SearchApiState.SUCCESS,
                            name = ""
                        )
                    }
                }
                else{
                    uiState.update { currentUiState ->
                        currentUiState.copy(
                            searchApiState = SearchApiState.NONE
                        )
                    }
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
    val category : String = "",
    val searchApiState : SearchApiState = SearchApiState.NONE,
)