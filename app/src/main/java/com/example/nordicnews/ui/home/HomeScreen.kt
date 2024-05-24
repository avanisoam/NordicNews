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
                        id = "Test Deatil Page Image",
                        name = "Business Insider"
                    ),
                    author = "Sumanth MSV",
                    content= "Just type in the prompt of what you want — an email that asks for a full refund — hit create, and a full draft appears. It conveniently pulls in flight details from the previous email. It looks pretty close to what you want to send, but maybe you want to refine it further. In this case, a more elaborate email might increase the chances of getting the refund. “Help me write” will start rolling out as part of our Workspace updates. And just like with Smart Compose, you’ll see it get better over time.Since the early days of Street View, AI has stitched together billions of panoramic images, so people can explore the world from their device. At last year’s I/O we introduced Immersive View, which uses AI to create a high-fidelity representation of a place, so you can experience it before you visit.\n" +
                            "\n" +
                            "Now, we’re expanding that same technology to do what Maps does best: help you get where you want to go. Google Maps provides 20 billion kilometers of directions, every day — that’s a lot of trips. Now imagine if you could see your whole trip in advance. With Immersive View for routes you can, whether you're walking, cycling or driving.Of course, we want you to do more than just search photos — we also want to help you make them better. In fact, every month, 1.7 billion images are edited in Google Photos. AI advancements give us more powerful ways to do this. For example, Magic Eraser, launched first on Pixel, uses AI-powered computational photography to remove unwanted distractions. And later this year, using a combination of semantic understanding and generative AI, you can do much more with a new experience called Magic Editor.",
                    description = "",
                    publishedAt = "2024-05-01T15:06:39Z",
                    title = "Google I/O 2023: Making AI more helpful for everyone",
                    url = "https://www.businessinsider.com/mike-tyson-sued-for-punching-passenger-jetblue-flight-california-court-2024-5",
                    urlToImage = "https://raw.githubusercontent.com/avanisoam/NordicNews/main/MockImages/Mock_DetailImg.png",
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
            //verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            //Text("Your text here")
            item {
            FixedHeader(article = ArticleMockData.articleList[13])
           }
            item { Spacer(modifier = Modifier.height(50.dp)) }
            item {
                HorizontalCardListWithText(
                    onItemClick = {navigateToDetailScreen(it)},
                    articles = uiState.ArticleList.take(20)
                )
            }

            item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                ColorfulTabsList()
            }
            item { Spacer(modifier = Modifier.height(50.dp)) }

            item{
                Column(modifier = Modifier.padding(
                    start = 25.dp,
                    end = 25.dp,
                    bottom = 50.dp
                )) {
                    Text(
                        text = "All Articles",
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
                        articles = uiState.ArticleList,
                        //modifier = Modifier.padding(bottom = 20.dp)
                    )
                }
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