package com.example.nordicnews.ui.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nordicnews.R
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.shared.ArticleListV1
import com.example.nordicnews.ui.shared.Footer
import com.example.nordicnews.ui.theme.NordicNewsTheme

object BookmarksDestination : NavigationDestination {
    override val route = "bookmark"
    override val titleRes = R.string.bookmarks
    override val selectedIcon = R.drawable.pin_selected
    override val unSelectedIcon = R.drawable.pin
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    navigateToDetailScreen : (Article) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BookmarksViewModel = viewModel(factory = BookmarksViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = BookmarksDestination.titleRes))
                }
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item {
                Column(
                    modifier = Modifier.padding(
                        start = 25.dp,
                        end = 25.dp,
                        bottom = 50.dp
                    )
                ) {
                    Text(
                        text = "All Bookmarks",
                        fontWeight = FontWeight(700),
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(29, 27, 32),
                        modifier = Modifier.padding(
                            //start = 25.dp,
                            //end = 25.dp,
                            bottom = 20.dp
                        )
                    )

                    ArticleListV1(
                        onItemClick = { navigateToDetailScreen(it) },
                        // Mock Article Data
                        //articles = ArticleMockData.articleList

                        // Data from Api
                        articles = uiState.bookmarks,
                        //modifier = Modifier.padding(bottom = 20.dp)
                    )
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun BookmarkScreenPreview() {
    NordicNewsTheme {
        BookmarksScreen(navController = rememberNavController(),
            navigateToDetailScreen = {})
    }
}