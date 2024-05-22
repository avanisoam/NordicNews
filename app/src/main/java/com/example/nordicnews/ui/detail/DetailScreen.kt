package com.example.nordicnews.ui.detail

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.example.nordicnews.data.models.Source
import com.example.nordicnews.ui.navigation.BottomNavigationBar
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.search.SearchDestination
import com.example.nordicnews.ui.theme.NordicNewsTheme
import com.google.gson.Gson
import kotlinx.coroutines.launch

object DetailDestination : NavigationDestination {
    override val route = "detail"
    override val titleRes = R.string.detail
    override val iconVector = Icons.Filled.Home
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    article : Article?,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory)
    //viewModel: DetailViewModel = viewModel()
) {
    if(article == null)
    {
        Text(text = "Something went wrong!")
    }
    else {
        val uiState by viewModel.uiState.collectAsState()
        val coroutineScope = rememberCoroutineScope()

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
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                            viewModel.saveItem(article)
                    }
                }) {

                    if (uiState.article.url == "") {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Bookmark Removed")
                    } else {
                        Icon(Icons.Default.Favorite, contentDescription = "Bookmark Added")
                    }
                }
            }
        ) { innerPadding ->

                DetailBody(
                    uiState = uiState,
                    article = article,
                    modifier = Modifier.padding(innerPadding))
            /*
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                // text = "Nordic News - ${stringResource(DetailDestination.titleRes)}",
                text = "Nordic News - ${article.toString()}",
                modifier = modifier
            )
        }
*/
        }
    }
}

@Composable
fun DetailBody(
    uiState : DetailUiSate,
    article : Article,
    modifier : Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .border(BorderStroke(2.dp, Color.Red))
        .height(100.dp)
    ) {
    Column {
        /*
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current) //samplePhoto.imgSrc,
                .data(article.urlToImage)
                .crossfade(true)
                .build(),
            //error = painterResource(R.drawable.ic_broken_image),
            //placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.news_thumbnail),
            contentScale = ContentScale.Crop,  // to cover whole screen
            modifier = Modifier
                .fillMaxWidth()
            //.align(Alignment.Center)
            //.clip(MaterialTheme.shapes.small)
            //.size(width = 80.dp, height = 80.dp),
        )

         */

        Text(
            text = article.source.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                //top= 4.dp,
                //start = 8.dp,
                //bottom = 8.dp
            )
        )
        Text(
            text = uiState.article.url,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                //top= 4.dp,
                //start = 8.dp,
                //bottom = 8.dp
            )
        )


    }
}
}

@Preview(showSystemUi = true)
@Composable
private fun DetailScreenPreview() {
    NordicNewsTheme {
        val article = Article(
            source = Source(
                id = "bbc-news",
                name = "BBC News"
            ),
            author = "https://www.facebook.com/bbcnews",
            content= "Birmingham Airport has temporarily suspended flights due to a security incident on a plane.\\r\\nA spokesperson for the airport, based in Solihull, said: \\\"The aircraft landed safely and all passengers an… [+460 chars]",
            description = "An airport spokesperson says the plane landed safely and all passengers and crew have disembarked.",
            publishedAt = "2024-04-16T16:42:23Z",
            title = "Birmingham Airport suspends operations over security incident",
            url = "https://www.bbc.co.uk/news/uk-england-birmingham-68831165",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/A44D/production/_116316024_breaking-promo-v20e2-red-976x549.png",
        )
        /*
        DetailScreen(
            navController = rememberNavController(),
            article = article)

         */
    }
}