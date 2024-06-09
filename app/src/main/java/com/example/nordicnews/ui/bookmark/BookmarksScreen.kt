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
import androidx.compose.ui.text.style.TextAlign
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
import com.example.nordicnews.ui.shared.Footer
import com.example.nordicnews.ui.theme.NordicNewsTheme
import org.koin.androidx.compose.getViewModel

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
    viewModel: BookmarksViewModel = getViewModel<BookmarksViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(
                            id = BookmarksDestination.titleRes
                        ),
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.headlineSmall,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )
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
            modifier = Modifier.padding(innerPadding)
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item {
                Column(
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 50.dp
                    )
                ) {
                    ArticleListV1(
                        onItemClick = { navigateToDetailScreen(it) },
                        // Data from Api
                        articles = uiState.bookmarks
                    )
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun BookmarkScreenPreview() {
    NordicNewsTheme(darkTheme = true) {
        BookmarksScreen(navController = rememberNavController(),
            navigateToDetailScreen = {})
    }
}