package com.example.nordicnews.ui.detail

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.nordicnews.ui.shared.getOffset
import com.example.nordicnews.ui.theme.NordicNewsTheme
import com.google.gson.Gson
import kotlinx.coroutines.launch

/*
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

            DetailBodyV2(
                //uiState = uiState,
                article = article,
                modifier = Modifier
                    .padding(innerPadding))
                /*
                DetailBody(
                    uiState = uiState,
                    article = article,
                    modifier = Modifier.padding(innerPadding))

                 */
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

@Composable
fun DetailBodyV1(
    uiState : DetailUiSate,
    article : Article,
    modifier : Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .fillMaxSize()
        .border(BorderStroke(2.dp, Color.Red))
        //.height(100.dp)

    ) {
        Column(modifier
            //.padding(8.dp)
            //.verticalScroll(rememberScrollState())
            .border(BorderStroke(2.dp, Color.Blue))
        ) {
            Text(
                text = article.title,
                fontSize = 22.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp)
                )


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
                    .height(390.dp)
                //.align(Alignment.Center)
                //.clip(MaterialTheme.shapes.small)
                //.size(width = 80.dp, height = 80.dp),
            )

            /*
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

             */


        }
    }
}

@Composable
fun DetailBodyV2(article : Article,
               modifier : Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            //start = 16.dp,
            //end = 8.dp,
            //top = 40.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ){
        item {
            Text(
                text = article.description,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight(400),
                fontSize = 28.sp,
                lineHeight = 36.sp,
                color = Color(29, 27, 32),
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 40.dp,
                    bottom = 28.dp
                )
            )
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(article.urlToImage)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                    //.clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
            //Spacer(modifier = Modifier.height(24.dp))
        item {
            DetailsRow(article)
        }
            //Spacer(modifier = Modifier.height(24.dp))
        item {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp,end=16.dp)

            )
        }
    item {
            Text(
                text = article.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp,end=16.dp)
            )
    }
    item {
            Text(
                text = article.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp,end=16.dp)
            )
        }

    }
}

@Composable
fun DetailsRow(article : Article,
               modifier : Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(article.urlToImage)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp)
                //.clip(shape = RoundedCornerShape(50.dp))
                .clip(MaterialTheme.shapes.small)
                .weight(1f),
            contentScale = ContentScale.Crop
        )

        Column(modifier
            .padding(16.dp)
            .weight(2f)) {
            Text(text = article.author)
            Text(text = getOffset(article.publishedAt))
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

 */