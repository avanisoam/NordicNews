package com.example.nordicnews.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.dataStore.UserPreferencesRepository
import com.example.nordicnews.ui.developerOptions.DeveloperOptionsUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingViewModel(private val userPreferencesRepository: UserPreferencesRepository) : ViewModel(){
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NordicNewsApplication)
                val userPreferencesRepository = application.userPreferencesRepository
                SettingViewModel(userPreferencesRepository=userPreferencesRepository)
            }
        }
    }

    val uiState : StateFlow<SettingUiState> = userPreferencesRepository.currentUserName
        .map { userName ->
            SettingUiState(userName = userName )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingUiState()
        )

    fun saveUserName(userName: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserName(userName)
        }
    }


}

data class SettingUiState(
    val userName : String = ""
)