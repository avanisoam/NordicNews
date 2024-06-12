package com.avanisoam.nordicnews.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avanisoam.nordicnews.data.model.Article
import com.avanisoam.nordicnews.data.model.Business
import com.avanisoam.nordicnews.data.model.General
import com.avanisoam.nordicnews.data.model.Sports
import com.avanisoam.nordicnews.data.model.Technology
import com.avanisoam.nordicnews.data.repository.ApiRepository
import com.avanisoam.nordicnews.dataStore.UserPreferencesRepository
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
    val isLiteDisplayMode: StateFlow<Boolean> =
        userPreferencesRepository.isLiteMode.map { it }.stateIn(
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