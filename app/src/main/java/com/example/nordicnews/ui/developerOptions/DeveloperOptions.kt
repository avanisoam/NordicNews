package com.example.nordicnews.ui.developerOptions

import android.text.Spannable.Factory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nordicnews.R
import com.example.nordicnews.ui.bookmark.BookmarksViewModel
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.settings.SettingsDestination

object DeveloperOptionsDestination : NavigationDestination {
    override val route = "developerOptions"
    override val titleRes = R.string.developerOptions
    override val selectedIcon = R.drawable.home_selected
    override val unSelectedIcon = R.drawable.home
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeveloperOptionsScreen(
    navigateUp : () -> Unit,
    modifier : Modifier = Modifier,
    navController: NavController,
    viewModel: DeveloperOptionsViewModel = viewModel(factory = DeveloperOptionsViewModel.Factory )
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = DeveloperOptionsDestination.titleRes))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ){innerPadding->
       
        Column {
            Text(
                text = "Developer Options Screen",
                modifier = Modifier.padding(innerPadding)
            )
            Row {
                Text(text = uiState.isDebugModeEnabled.toString())
                Button(onClick = { viewModel.toggleDebugMode() }) {
                    Text(text = "Toggle state")
                }
            }
        }
    }

}