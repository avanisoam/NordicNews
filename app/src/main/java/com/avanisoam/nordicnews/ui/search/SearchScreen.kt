package com.avanisoam.nordicnews.ui.search

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.avanisoam.nordicnews.R
import com.avanisoam.nordicnews.data.model.Article
import com.avanisoam.nordicnews.ui.navigation.NavigationDestination
import com.avanisoam.nordicnews.ui.shared.ArticleListV1
import com.avanisoam.nordicnews.ui.shared.ErrorScreen
import com.avanisoam.nordicnews.ui.shared.Footer
import com.avanisoam.nordicnews.ui.shared.LoadingScreen
import com.avanisoam.nordicnews.ui.theme.NordicNewsTheme
import org.koin.androidx.compose.getViewModel

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
    viewModel: SearchViewModel = getViewModel<SearchViewModel>()
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
             SearchApiState.SUCCESS ->
                 SearchResult(
                    topic = uiState.category,
                    navigateToDetailScreen = navigateToDetailScreen,
                    articles = uiState.articleList,
                     modifier = Modifier
                         .padding(innerPadding)
                         //.padding(15.dp)
                )
             SearchApiState.ERROR -> ErrorScreen(
                 modifier = Modifier.fillMaxSize()
             )
             SearchApiState.NONE -> Text(
                text = "No result found!",
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(15.dp)
             )
        }
    }
}

@Composable
fun SearchResult(
    topic : String,
    navigateToDetailScreen :(Article) -> Unit,
    articles : List<Article>,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        //item { Spacer(modifier = Modifier.height(60.dp)) }
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