package com.example.nordicnews.ui.search

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.models.Business
import com.example.nordicnews.data.models.Category
import com.example.nordicnews.data.models.General
import com.example.nordicnews.data.models.Technology
import com.example.nordicnews.data.network.ApiRepository
import com.example.nordicnews.dataStore.UserPreferencesRepository
import com.example.nordicnews.ui.detail.DetailUiState
import com.example.nordicnews.ui.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    savedStateHandle: SavedStateHandle,
    private  val apiRepository: ApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NordicNewsApplication)
                val apiRepository = application.container.apiRepository
                val savedStateHandle = this.createSavedStateHandle()
                val userPreferencesRepository = application.userPreferencesRepository
                SearchViewModel(savedStateHandle,apiRepository = apiRepository,userPreferencesRepository=userPreferencesRepository)
            }
        }
    }

    private val category: String? = savedStateHandle[SearchDestination.categoryArg]

    val userName: StateFlow<String> = userPreferencesRepository.userName()
        .filter {
            it.isNotEmpty()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ""
        )

    var uiState = MutableStateFlow(SearchUiState())
        private set

    fun onNameChange(value : String) {
        
        uiState.update {currentUiState ->
            currentUiState.copy(
                name = value
            )
        }
        
        //getFilteredNews(value)
        saveUserName(value)
    }

    fun onSearchClicked(value : String) {
        getFilteredNews(value)
        /*
        uiState.update {currentUiState ->
            currentUiState.copy(
                name = ""
            )
        }
        
         */
    }

    fun getFilteredNews(sources : String = "ars-technica",//"the-verge",//"bbc-news",
                   page : Int= 1){
        viewModelScope.launch {
            try {
                //val allNews = apiRepository.getNews(sources,page)
                val allNews = apiRepository.getHeadlinesByCountryAndCategoryV1(sources)

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

    fun searchNews(keyword : String) {
        viewModelScope.launch {
            try {
                val allNews = apiRepository.searchNewsV2(keyword)
                Log.d("AllNews", "${allNews.totalResults} no.of news")
                uiState.update {currentUiState ->
                    currentUiState.copy(
                        ArticleList = allNews.articles
                    )
                }
            }catch (ex : Exception){

            }
        }
    }

    fun CustomInit()
    {
        uiState.update {currentUiState ->
            currentUiState.copy(
                name = userName.value
            )
        }
    }

    init {

        if(category != null) {
            uiState.update { currentUiState ->
                currentUiState.copy(
                    category = category,
                )
            }
            getFilteredNews(uiState.value.category.lowercase())

            saveUserName(category)
        }
    }

    fun saveUserName(name: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserName(name)
        }
    }
}
enum class SearchWidgetState {
    OPENED,
    CLOSED
}
data class SearchUiState(
    val searchWidgetState : SearchWidgetState = SearchWidgetState.OPENED,
    val name : String = "",
    val ArticleList : List<Article> = listOf(),
   val category : String = "Everything"
)