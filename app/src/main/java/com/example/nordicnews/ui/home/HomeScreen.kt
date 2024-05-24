package com.example.nordicnews.ui.home

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nordicnews.R
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.models.ArticleMockData
import com.example.nordicnews.data.models.Source
import com.example.nordicnews.ui.navigation.BottomNavigationBar
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.shared.ArticleList
import com.example.nordicnews.ui.shared.ArticleListV1
import com.example.nordicnews.ui.shared.ColorfulTabsList
import com.example.nordicnews.ui.shared.FixedHeader
import com.example.nordicnews.ui.shared.HorizontalCardListWithText
import com.example.nordicnews.ui.theme.NordicNewsTheme
import com.google.gson.Gson


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home
    override val iconVector = Icons.Filled.Home
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToDetailScreen : (Article) -> Unit,
    navController: NavController,
    //name: String,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(id = HomeDestination.titleRes))
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
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {

                val article = Article(
                    source = Source(
                        id = "bbc-news",
                        name = "BBC News"
                    ),
                    author = "https://www.facebook.com/bbcnews",
                    content = "Birmingham Airport has temporarily suspended flights due to a security incident on a plane.\\r\\nA spokesperson for the airport, based in Solihull, said: \\\"The aircraft landed safely and all passengers an… [+460 chars]",
                    description = "An airport spokesperson says the plane landed safely and all passengers and crew have disembarked.",
                    publishedAt = "2024-04-16T16:42:23Z",
                    title = "Birmingham Airport suspends operations over security incident",
                    url = "https://www.bbc.co.uk/news/uk-england-birmingham-68831165",
                    urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/A44D/production/_116316024_breaking-promo-v20e2-red-976x549.png",
                )
                val json = Uri.encode(Gson().toJson(article))
                navController.navigate("detail/$json")

                //navController.navigate(DetailDestination.route)

            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                //.verticalScroll(rememberScrollState())
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            //Text("Your text here")
            item {
            FixedHeader(article = ArticleMockData.articleList[3])
           }
            item { Spacer(modifier = Modifier.height(50.dp)) }
            item {
                HorizontalCardListWithText(articles = uiState.ArticleList.take(20))
            }

            item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                ColorfulTabsList()
            }
            item { Spacer(modifier = Modifier.height(50.dp)) }

            item{

                    ArticleListV1(
                        onItemClick = { navigateToDetailScreen(it) },
                        // Mock Article Data
                        //articles = ArticleMockData.articleList

                        // Data from Api
                        articles = uiState.ArticleList,
                        //modifier = Modifier.height(500.dp)
                    )
            }

        }
    }
}
            /*
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button $presses times.
                """.trimIndent(),
            )
            
             */
            //Text(text = "No. of Article ${uiState.ArticleList.size}")



@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    NordicNewsTheme {
        HomeScreen(navController = rememberNavController(),
            navigateToDetailScreen = {})
    }
}