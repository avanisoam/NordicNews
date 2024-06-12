package com.avanisoam.nordicnews.ui.detail

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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.avanisoam.nordicnews.R
import com.avanisoam.nordicnews.data.model.Article
import com.avanisoam.nordicnews.data.model.ArticleMockData
import com.avanisoam.nordicnews.data.model.Source
import com.avanisoam.nordicnews.ui.navigation.NavigationDestination
import com.avanisoam.nordicnews.ui.shared.ArticleListV1
import com.avanisoam.nordicnews.ui.shared.Footer
import com.avanisoam.nordicnews.ui.shared.getOffset
import com.avanisoam.nordicnews.ui.theme.NordicNewsTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


object DetailDestination : NavigationDestination {

    override val route = "detail"
    const val ARTICLE_ARG = "article"
    val routeWithArgs = "${route}/{$ARTICLE_ARG}"
    override val titleRes = R.string.detail
    override val selectedIcon = R.drawable.ic_general
    override val unSelectedIcon = R.drawable.ic_general
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateToDetailScreen : (Article) -> Unit,
    article : Article?,
    navController: NavController,
    navigateUp: () -> Unit = {},
    viewModel : DetailViewModel = getViewModel<DetailViewModel>()
) {
    val context = LocalContext.current
    article?.let {
        val uiState by viewModel.uiState.collectAsState()
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(id = DetailDestination.titleRes),
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.headlineSmall,
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Center
                        )
                    },
                    actions = {
                        // RowScope here, so these icons will be placed horizontally
                        if (uiState.article.url == "") {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.saveItem(article)
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.pin_hollow
                                    ),
                                    contentDescription = null
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.deleteItem(article)
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.pin
                                    ),
                                    contentDescription = null
                                )
                            }
                        }

                        IconButton(
                            onClick = { viewModel.shareOrder(context) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.share),
                                contentDescription = null
                            )
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
                    containerColor = Color.White,
                ) {
                    Footer(navController)
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
            ) {
                item {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight(400),
                        fontSize = 28.sp,
                        lineHeight = 36.sp,
                        color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 40.dp,
                            bottom = 28.dp
                        )
                    )
                }
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(
                            context = LocalContext.current
                        ).data(article.urlToImage).build(),
                        contentDescription = null,
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                item {
                    DetailsRow(article = article)
                }
                item {
                    Divider(
                        color = Color(215, 215, 215),
                        modifier = Modifier.padding(
                                start = 10.dp,
                                end = 10.dp,
                                bottom = 20.dp
                        ),
                        thickness = 1.dp
                    )
                }

                item {
                    Column(
                        modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 28.dp
                        )
                    ) {
                        Text(
                            text = article.content,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            lineHeight = 24.sp,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
                            modifier = Modifier.padding(bottom=20.dp),
                            textAlign = TextAlign.Left
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier.padding(start=10.dp, end=10.dp)
                    ) {
                        Text(
                            text = "Related article",
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700),
                            lineHeight = 28.sp,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
                            modifier = Modifier.padding(bottom=20.dp)
                        )

                        ArticleListV1(
                            onItemClick = { navigateToDetailScreen(it) },
                            // Mock Article Data
                            articles = ArticleMockData.articleList.takeLast(3)
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(84.dp))
                }
            }
        }
    } ?: run {
        Text(text = "Something went wrong!")
    }
}

@Composable
fun DetailBody(
    uiState : DetailUiState,
    article : Article,
    modifier : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(BorderStroke(2.dp, Color.Red))
            .height(100.dp)
    ) {
        Column {
            Text(
                text = article.source.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding()
            )
            Text(
                text = uiState.article.url,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding()
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

    ) {
        Column(
            modifier = modifier.border(
                BorderStroke(2.dp, Color.Blue)
            )
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
                model = ImageRequest.Builder(
                    context = LocalContext.current
                )
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.news_thumbnail),
                contentScale = ContentScale.Crop,  // to cover whole screen
                modifier = Modifier
                    .fillMaxWidth()
                    .height(390.dp)
            )
        }
    }
}

@Composable
fun DetailBodyV2(article : Article) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(),
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
                model = ImageRequest.Builder(
                    context = LocalContext.current
                )
                    .data(article.urlToImage)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
        }
        item {
            DetailsRow(article)
        }
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
fun DetailsRow(article : Article) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                bottom = 19.dp,
                end = 10.dp
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(article.urlToImage)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 32.dp)
                .size(width = 35.dp, height = 35.dp)
                .clip(CircleShape),
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
                color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
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
                    color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding()
                )
                Text(
                    text = "${getOffset(article.publishedAt)} ago",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 17.41.sp,
                    color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
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
    }
}