package com.example.nordicnews.ui.developerOptions

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nordicnews.R
import com.example.nordicnews.data.Constants
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.shared.SettingsSwitchComp

object DeveloperOptionsDestination : NavigationDestination {
    override val route = "developerOptions"
    override val titleRes = R.string.developerOptions
    override val selectedIcon = R.drawable.ic_tech
    override val unSelectedIcon = R.drawable.ic_tech
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeveloperOptionsScreen(
    navigateUp: () -> Unit,
    viewModel: DeveloperOptionsViewModel = viewModel(
        factory = DeveloperOptionsViewModel.Factory
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(
                            id = DeveloperOptionsDestination.titleRes
                        )
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
                icon = Icons.Filled.Build,
                iconDesc = "Toggle Switch",
                name = "Test Mode",
                state = uiState.isDebugModeEnabled
            ) {
                viewModel.toggleDebugMode()
            }
            if (uiState.isDebugModeEnabled) {
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "Test API URL : ${Constants.DEV_BASE_URL}",
                    modifier = Modifier.padding(start = 25.dp),
                )
            } else {
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "Prod API URL : ${Constants.PROD_BASE_URL}",
                    modifier = Modifier.padding(start = 25.dp),
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))

            val context = LocalContext.current

            // https://stackoverflow.com/questions/72932093/jetpack-compose-is-there-a-way-to-restart-whole-app-programmatically
            // https://stackoverflow.com/questions/77740044/issue-with-localcontext-current-composable-invocations-can-only-happen-from-th
            // Restart Button
            Button(
                onClick = {
                    val packageManager: PackageManager = context.packageManager
                    val intent: Intent? = packageManager.getLaunchIntentForPackage(
                        context.packageName
                    )
                    val componentName: ComponentName? = intent?.component
                    val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
                    context.startActivity(restartIntent)
                    Runtime.getRuntime().exit(0)
                },
                modifier = Modifier.padding(top = 8.dp, start = 25.dp)
            ) {
                Text(text = "Restart")
            }
        }
    }
}