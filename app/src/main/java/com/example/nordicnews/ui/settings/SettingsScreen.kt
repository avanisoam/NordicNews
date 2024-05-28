package com.example.nordicnews.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nordicnews.R
import com.example.nordicnews.ui.detail.DetailDestination
import com.example.nordicnews.ui.developerOptions.DeveloperOptionsViewModel
import com.example.nordicnews.ui.navigation.BottomNavigationBar
import com.example.nordicnews.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings
    override val selectedIcon = R.drawable.ic_business
    override val unSelectedIcon = R.drawable.ic_business
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateUp : () -> Unit,
    viewModel: SettingViewModel = viewModel(factory = SettingViewModel.Factory )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = SettingsDestination.titleRes))
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
    ){ innerPadding->
        MainScreen(

            uiState = uiState,
            modifier = Modifier.padding(innerPadding),
            onClick = {viewModel.saveUserName(it)},
        )
    }

}

@Composable
fun MainScreen(
    uiState: SettingUiState,
    modifier: Modifier = Modifier,
    onClick : (String) -> Unit,
) {

    var userInput by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Topic: ${uiState.userName}",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .padding(top = 32.dp)
        )
        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            modifier = Modifier
                .padding(vertical = 32.dp)
        )
        Button(
            onClick = { onClick(userInput) }
        ) {
            Text(text = "SAVE Topic")
        }
    }
}