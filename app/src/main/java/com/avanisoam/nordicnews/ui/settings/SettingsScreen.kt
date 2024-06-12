package com.avanisoam.nordicnews.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avanisoam.nordicnews.R
import com.avanisoam.nordicnews.ui.navigation.NavigationDestination
import com.avanisoam.nordicnews.ui.shared.SettingsSwitchComp

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
                    Text(
                        stringResource(id = SettingsDestination.titleRes),
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.headlineSmall,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )
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
    ) { innerPadding->

        Column(modifier = Modifier.padding(innerPadding)) {
            Spacer(modifier = Modifier.padding(4.dp))

            SettingsSwitchComp(
                icon = Icons.Filled.Settings,
                iconDesc = "Toggle Switch",
                name = "Lite Mode",
                state = uiState.isLiteModeOn
            ) {
                viewModel.toggleSwitch()
            }
        }
    }
}