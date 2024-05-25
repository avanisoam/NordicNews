package com.example.nordicnews.ui.detail

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nordicnews.R
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.models.ArticleMockData
import com.example.nordicnews.data.models.Source
import com.example.nordicnews.ui.navigation.BottomNavigationBar
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.shared.ArticleListV1
import com.example.nordicnews.ui.shared.getOffset
import com.example.nordicnews.ui.theme.NordicNewsTheme
import kotlinx.coroutines.launch


object DetailDestination : NavigationDestination {
    override val route = "detail/{article}"
    override val titleRes = R.string.detail
    override val iconVector = Icons.Filled.Home
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateToDetailScreen : (Article) -> Unit,
    article : Article?,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory),
    navigateUp: () -> Unit = {}
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
                CenterAlignedTopAppBar(
                    title = {
                        Text(stringResource(id = DetailDestination.titleRes))
                    },
                    actions = {
                        // RowScope here, so these icons will be placed horizontally
                        if (uiState.article.url == "") {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    viewModel.saveItem(article)
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.pin_hollow ) ,
                                    contentDescription = null)
                            }
                        } else {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    viewModel.deleteItem(article)
                                    //navigateUp()
                                    // To refresh screen
                                    //navigateToDetailScreen(article)
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.pin ) ,
                                    contentDescription = null)
                            }
                        }

                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.share ) ,
                                contentDescription = null)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = navigateUp) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button)
                            )
                        }
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

            LazyColumn(
                modifier = Modifier
                    //.verticalScroll(rememberScrollState())
                    .padding(innerPadding),
                //verticalArrangement = Arrangement.spacedBy(16.dp),
            ){
                item {
                    Text(
                        text = article.title ,
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
                /*
                item {
                    Text(text = "${uiState.article.url}**")
                }
                 */
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(article.urlToImage)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            //.width(390.dp)
                            .height(220.dp),
                            //.border(BorderStroke(2.dp, Color.Red)),
                        //.clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                }
                item {
                    DetailsRow(article = article)
                }
                item{

                    Divider(color = Color(215, 215, 215),
                        modifier =Modifier
                        //.height(1.dp)
                        //.width(1.dp)
                        .padding(
                            start = 25.dp,
                            end = 25.dp,
                            bottom = 20.dp
                        ),
                        thickness = 1.dp

                     )
                }

                item {
                    Column(modifier = Modifier.
                    padding(start = 25.dp, end = 25.dp, bottom = 28.dp)) {
                        Text(
                            text = "AI in our products",
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700),
                            lineHeight = 28.sp,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(29, 27, 32),
                            modifier = Modifier.padding(top=20.dp,bottom=20.dp)
                        )
                        Text(
                            text = "“Help me write” in Gmail",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 22.sp,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(29, 27, 32),
                            modifier = Modifier.padding(bottom=10.dp)
                        )
                        Text(
                            text = article.content,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            lineHeight = 24.sp,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(29, 27, 32),
                            modifier = Modifier.padding(bottom=20.dp),
                            textAlign = TextAlign.Justify
                        )
                    }
                }
                item{
                    Column(modifier = Modifier.padding(start=25.dp,end=25.dp)) {
                        Text(
                            text = "Related article",
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700),
                            lineHeight = 28.sp,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(29, 27, 32),
                            modifier = Modifier.padding(bottom=20.dp),
                            )

                        ArticleListV1(
                            onItemClick = { navigateToDetailScreen(it)},
                            // Mock Article Data
                            articles = ArticleMockData.articleList.takeLast(3)
                            // Data from Api
                            //articles = uiState.ArticleList,
                            //modifier = Modifier.height(500.dp)
                        )
                    }
                }
                item { 
                    Spacer(modifier = Modifier.height(84.dp))
                }
            }
            /*
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

             */
        }
    }


}

@Composable
fun DetailBody(
    uiState : DetailUiState,
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
    uiState : DetailUiState,
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
                text = article.title,
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
            .padding(
                //top = 32.dp,
                start = 25.dp,
                bottom = 19.dp,
                end = 25.dp
            )
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(article.urlToImage)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 32.dp)
                //.size(64.dp)
                .size(width = 35.dp, height = 35.dp)
                .clip(CircleShape),
                //.border(BorderStroke(2.dp, Color.Red))
                //.clip(MaterialTheme.shapes.small),
                //.weight(1f),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier
            .padding(start = 12.dp, top = 30.dp)
            .weight(2f)) {
            Text(
                text = article.author.ifEmpty { "Anonymous" },
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                lineHeight = 22.sp,
                color = Color(29, 27, 32),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(
                    end = 18.dp,
                    bottom = 1.dp
                )
            )
            Row {
                Text(
                    text = "posted :",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 17.41.sp,
                    color = Color(29, 27, 32),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(
                        //start = 0.5.dp,
                        //end = 0.5.dp
                    )
                )
                Text(
                    text = "${getOffset(article.publishedAt)} ago",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 17.41.sp,
                    color = Color(29, 27, 32),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(
                        start = 7.dp
                    )
                )
            }
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