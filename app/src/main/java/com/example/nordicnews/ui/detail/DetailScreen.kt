package com.example.nordicnews.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nordicnews.R
import com.example.nordicnews.ui.navigation.BottomNavigationBar
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.search.SearchDestination
import com.example.nordicnews.ui.theme.NordicNewsTheme

object DetailDestination : NavigationDestination {
    override val route = "detail"
    override val titleRes = R.string.detail
    override val iconVector = Icons.Filled.Home
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(id = DetailDestination.titleRes))
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,//MaterialTheme.colorScheme.primaryContainer,
                //contentColor = Color.Yellow,//MaterialTheme.colorScheme.primary,
            ) {
                BottomNavigationBar(navController)
            }
        }
    ){innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Nordic News - ${stringResource(DetailDestination.titleRes)}",
                modifier = modifier
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun DetailScreenPreview() {
    NordicNewsTheme {
        DetailScreen(navController = rememberNavController())
    }
}