package com.example.nordicnews.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nordicnews.R
import com.example.nordicnews.data.model.Article
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
            SearchTopAppBar(
                searchWidgetState = uiState.searchWidgetState,
                searchTextState = uiState.name,
                onTextChange = {viewModel.onNameChange(it)},
                onCloseClicked = { viewModel.onCrossButtonClick(newValue = SearchWidgetState.CLOSED ) },//{ viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED ) },
                onSearchClicked = {viewModel.searchNews(it)},
                onSearchTriggered = {viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)}
            )

        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
            ) {
                Footer(navController)
            }
        }
    ) { innerPadding ->
        when (uiState.searchApiState) {
             SearchApiState.LOADING -> LoadingScreen(
                 modifier = Modifier.fillMaxSize()
             )
             SearchApiState.SUCCESS -> SearchResult(
                    topic = uiState.category,
                    navigateToDetailScreen = navigateToDetailScreen,
                    articles = uiState.articleList
                )
             SearchApiState.ERROR -> ErrorScreen(
                 modifier = Modifier.fillMaxSize()
             )
            SearchApiState.NONE -> LoadingScreen(modifier = Modifier.fillMaxSize().padding(innerPadding))
            /*
             SearchApiState.NONE -> Text(
                text = "No result found!",
                modifier = Modifier.padding(innerPadding).padding(15.dp)
             )
             */
        }
    }
}

@Composable
fun SearchResult(
    topic : String,
    navigateToDetailScreen :(Article) -> Unit,
    articles : List<Article>
) {
    LazyColumn {
        item { Spacer(modifier = Modifier.height(60.dp)) }
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = topic,
                    style = MaterialTheme.typography.headlineLarge,
                    //fontWeight = FontWeight.Bold
                    fontSize = 28.sp,
                    lineHeight = 36.sp,
                    modifier = Modifier.padding(start =10.dp)
                )
            }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            Column(
                modifier = Modifier.padding(start = 10.dp,end = 10.dp)
            ) {
                ArticleListV1(
                    onItemClick = { navigateToDetailScreen(it) },
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