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
import com.example.nordicnews.data.models.Category
import com.example.nordicnews.data.models.General
import com.example.nordicnews.data.models.Technology
import com.example.nordicnews.data.network.ApiRepository
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

    var categoryuiState = MutableStateFlow<List<Category>>(
        value = listOf(General(),Business(),Technology())
    )
        private set

    var uiState = MutableStateFlow(HomeUiState())
        private set

   private fun getNewsBySource(source : String){
        viewModelScope.launch {
            try {
                val allNews = apiRepository.getNewsBySource(source)
                //val allNews = apiRepository.getNewsByApi()
                Log.d("NEWS123","${allNews.totalResults} data")
                uiState.update {currentUiState ->
                    currentUiState.copy(
                        ArticleList = allNews.articles
                    )
                }
            }catch (e: IOException){
                Log.d("Error:NEWS123","${e.toString()} data")
            }
        }
    }


    private fun checkServerStatus(){
        viewModelScope.launch {

                val ping = apiRepository.ping()
            Log.d("PING","${ping} data")
                //val allProducts = apiRepository.getProducts()
                //Log.d("PING","${allProducts.size} data")
                /*
                uiState.update {currentUiState ->
                    currentUiState.copy(
                        ArticleList = allNews.articles
                    )
                }

                 */
        }
    }


    init {
       getNewsBySource(source = "the-verge")
        //checkServerStatus()
    }

}

data class HomeUiState(
    val ArticleList : List<Article> = listOf()
)