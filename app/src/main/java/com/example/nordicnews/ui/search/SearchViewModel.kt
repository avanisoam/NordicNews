package com.example.nordicnews.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.network.ApiRepository
import com.example.nordicnews.ui.detail.DetailUiSate
import com.example.nordicnews.ui.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(private  val apiRepository: ApiRepository) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NordicNewsApplication)
                val apiRepository = application.container.apiRepository
                SearchViewModel(apiRepository = apiRepository)
            }
        }
    }

    var uiState = MutableStateFlow(SearchUiState())
        private set

    fun onNameChange(value : String) {
        uiState.update {currentUiState ->
            currentUiState.copy(
                name = value
            )
        }
        //getFilteredNews(value)
    }

    fun onSearchClicked(value : String) {
        getFilteredNews(value)
    }

    fun getFilteredNews(sources : String = "ars-technica",//"the-verge",//"bbc-news",
                   page : Int= 1){
        viewModelScope.launch {
            try {
                val allNews = apiRepository.getNews(sources,page)

                uiState.update {currentUiState ->
                    currentUiState.copy(
                        ArticleList = allNews.articles
                    )
                }
            }//catch (e: IOException) {
            catch (ex : Exception) {

            }
        }
    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {

        uiState.update {currentUiState ->
            currentUiState.copy(
                searchWidgetState = newValue
            )
        }
    }

    init {
        getFilteredNews()
    }
}
enum class SearchWidgetState {
    OPENED,
    CLOSED
}
data class SearchUiState(
    val searchWidgetState : SearchWidgetState = SearchWidgetState.OPENED,
    val name : String = "",
    val ArticleList : List<Article> = listOf()
)