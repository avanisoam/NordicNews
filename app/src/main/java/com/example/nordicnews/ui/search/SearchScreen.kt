package com.example.nordicnews.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nordicnews.R
import com.example.nordicnews.ui.home.HomeDestination
import com.example.nordicnews.ui.home.HomeViewModel
import com.example.nordicnews.ui.navigation.BottomNavigationBar
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.shared.ArticleList
import com.example.nordicnews.ui.theme.NordicNewsTheme

object SearchDestination : NavigationDestination {
    override val route = "search"
    override val titleRes = R.string.search
    override val iconVector = Icons.Filled.Search
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            /*
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    //Text(stringResource(id = SearchDestination.titleRes))
                    /*
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = {viewModel.onNameChange(it)},
                        //label = { Text(text = "Search") },
                        modifier = Modifier.fillMaxWidth()
                            //.padding(2.dp),
                    )*/
                }
            )
             */

            SearchTopAppBar(
                searchWidgetState = uiState.searchWidgetState,
                searchTextState = uiState.name,
                onTextChange = {viewModel.onNameChange(it)},
                onCloseClicked = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED ) },
                onSearchClicked = {viewModel.onSearchClicked(it)},
                onSearchTriggered = {viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)}
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if(uiState.name.isNotEmpty()) {
                Text(
                    text = "Search: ${uiState.name}",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else {
                /*
                Text(
                    text = "Nordic News - ${stringResource(SearchDestination.titleRes)}",
                    modifier = modifier
                )
                 */
                Text(text = "No. of Article ${uiState.ArticleList.size}")

            }
            ArticleList(articles = uiState.ArticleList,
                onItemClick = {})
        }
    }

}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    NordicNewsTheme {
       SearchScreen(
           navController = rememberNavController()
       )
    }
}