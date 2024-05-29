package com.example.nordicnews.ui.search

import android.os.Handler
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nordicnews.R
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.shared.ArticleListV1
import com.example.nordicnews.ui.shared.ErrorScreen
import com.example.nordicnews.ui.shared.Footer
import com.example.nordicnews.ui.shared.LoadingScreen
import com.example.nordicnews.ui.theme.NordicNewsTheme

object SearchDestination : NavigationDestination {

    override val route = "search"
    const val CATEGORY_ARG = "category"
    val routeWithArgs = "$route/{$CATEGORY_ARG}"

    override val titleRes = R.string.search

    override val selectedIcon = R.drawable.search_selected
    override val unSelectedIcon = R.drawable.search

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigateToDetailScreen : (Article) -> Unit,
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
                onSearchClicked = {viewModel.searchNews(it)},//{viewModel.onSearchClicked(it)},
                onSearchTriggered = {viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)}
            )

        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,//MaterialTheme.colorScheme.primaryContainer,
                //contentColor = Color.Yellow,//MaterialTheme.colorScheme.primary,
            ) {
                //BottomNavigationBar(navController)
                Footer(navController)
                //BottomBar(navController)
            }
        }
    ) { innerPadding ->
        when(uiState.searchApiState) {
         SearchApiState.LOADING -> LoadingScreen(modifier = Modifier.fillMaxSize())
         SearchApiState.SUCCESS -> SearchResult(
                topic = uiState.name,
                navigateToDetailScreen = navigateToDetailScreen,
                articles = uiState.articleList,
                modifier = Modifier.padding(innerPadding)
            )
        SearchApiState.ERROR ->    ErrorScreen( modifier = Modifier.fillMaxSize())
         SearchApiState.NONE ->
                Text(text = "None",modifier = Modifier.padding(innerPadding))
            else ->
                Text(text = "Else")   // For Otherwise
        }

    }
}

@Composable
fun SearchResult(modifier : Modifier = Modifier,
                 topic : String,
                 navigateToDetailScreen :(Article) -> Unit,
                 articles : List<Article>) {
    LazyColumn(){
        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "My Topic: $topic",
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            Column(modifier = Modifier.padding(start = 25.dp,end = 25.dp)) {
                ArticleListV1(
                    onItemClick = { navigateToDetailScreen(it) },
                    // Mock Article Data
                    //articles = ArticleMockData.articleList

                    // Data from Api
                    articles = articles,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    NordicNewsTheme {
       SearchScreen(
           navigateToDetailScreen = {},
           navController = rememberNavController()
       )
    }
}