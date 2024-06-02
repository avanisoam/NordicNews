package com.example.nordicnews.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.models.Business
import com.example.nordicnews.data.models.General
import com.example.nordicnews.data.models.Sports
import com.example.nordicnews.data.models.Technology
import com.example.nordicnews.data.network.ApiRepository
import com.example.nordicnews.dataStore.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(
    private  val apiRepository: ApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NordicNewsApplication)
                val apiRepository = application.container.apiRepository
                val userPreferencesRepository = application.userPreferencesRepository
                HomeViewModel(
                    apiRepository = apiRepository,
                    userPreferencesRepository=userPreferencesRepository
                    )
            }
        }
    }

    val isLiteDisplayMode: StateFlow<Boolean> =
        userPreferencesRepository.isLightMode.map { it }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500),
            false
        )

    var categoryUIState = MutableStateFlow(
        value = listOf(General(),Business(),Technology(), Sports())
    )
        private set

    var uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
        private set

   private fun getNewsBySource(source : String) {
       viewModelScope.launch {
           uiState.value =  try {
               val allNews = apiRepository.getNewsBySource(source)
               HomeUiState.Success(allNews.articles)
           } catch (_: IOException) {
                HomeUiState.Error
           }
       }
   }

    private fun checkServerStatus() {
        viewModelScope.launch {
            val ping = apiRepository.ping()
            Log.d("PING","$ping data")
        }
    }


    init {
       getNewsBySource(source = "the-verge")
    }

}

sealed interface HomeUiState {
    data class Success(val articleList : List<Article> = listOf()) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}