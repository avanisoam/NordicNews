package com.example.nordicnews.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.network.ApiRepository
import com.example.nordicnews.ui.detail.DetailViewModel
import com.example.nordicnews.ui.search.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(private  val apiRepository: ApiRepository): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NordicNewsApplication)
                val apiRepository = application.container.apiRepository
                HomeViewModel(apiRepository = apiRepository)
            }
        }
    }

    var uiState = MutableStateFlow(HomeUiState())
        private set

    fun getAllNews(sources : String = "ars-technica",//"the-verge",//"bbc-news",
                   page : Int= 1){
        viewModelScope.launch {
            try {
                val allNews = apiRepository.getNews(sources,page)
                uiState.update {currentUiState ->
                    currentUiState.copy(
                        ArticleList = allNews.articles
                    )
                }
            }catch (e: IOException){

            }
        }
    }

    init {
        getAllNews()
    }

}

data class HomeUiState(
    val ArticleList : List<Article> = listOf()
)