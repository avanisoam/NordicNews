package com.example.nordicnews.ui.developerOptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nordicnews.NordicNewsApplication
import com.example.nordicnews.dataStore.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DeveloperOptionsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    val uiState : StateFlow<DeveloperOptionsUiState> = userPreferencesRepository.isDebugMode
        .map { isDebugModeEnabled ->
            DeveloperOptionsUiState(isDebugModeEnabled)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DeveloperOptionsUiState()
        )

    fun toggleDebugMode(){
        viewModelScope.launch {
            userPreferencesRepository.savePreference(uiState.value.isDebugModeEnabled.not())
        }
    }
}

data class DeveloperOptionsUiState(
    val isDebugModeEnabled : Boolean = true
)