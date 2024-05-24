package com.example.nordicnews.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nordicnews.data.models.Article
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nordicnews.R

@Composable
fun HorizontalGrid(articles : List<Article>, modifier : Modifier = Modifier) {
    LazyHorizontalGrid(
        //columns = GridCells.Fixed(4),
        rows = GridCells.Fixed(2),
        //verticalArrangement = Arrangement.spacedBy(2.dp),
        //horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier.border(BorderStroke(2.dp, SolidColor(Color.Red)))) {
        items(articles){article ->
            //ArticleCard(article = article)
            TopicCard(article = article)
            //Text(text = "Hello World")
        }
    }
}

@Composable
fun TopicCard(article : Article,modifier: Modifier = Modifier){

    Box {
        Card(modifier = Modifier.border(BorderStroke(2.dp, SolidColor(Color.Blue)))) {
            Row {
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
                        //.align(Alignment.Center)
                        .clip(MaterialTheme.shapes.small)
                        .size(width = 80.dp, height = 80.dp),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(
                        text = sanitizeString(article.title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            //top= 4.dp,
                            //start = 8.dp,
                            //bottom = 8.dp
                        )
                    )

                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        Text(
                            text = article.source.name,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(
                                //start = 8.dp,
                                //bottom = 4.dp
                            )
                        )
                        Text(
                            text = ".",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                start = 5.dp,
                                //bottom = 4.dp
                            )
                        )
                        Text(
                            text = getOffset(article.publishedAt),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(
                                start = 5.dp,
                                //bottom = 4.dp
                            )
                        )
                    }
                }

            }
        }
    }
}

private fun sanitizeString(value : String) : String {
    var subString = value
    if(value.length > 57)
    {
        subString = value.substring(0,55) + "..."
    }
    return subString
}

val items = (100..200).toList()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalGrid(article : Article, modifier : Modifier = Modifier ) {
    val rows = 2
    val columns = 4
    val itemsPerPage = rows * columns
    val pages = items.size / itemsPerPage + if (items.size % itemsPerPage == 0) 0 else 1
    val pagerState = rememberPagerState(pageCount = { pages })
    HorizontalPager(state = pagerState) { page ->
        Column(modifier = Modifier.fillMaxSize()) {
            repeat(rows) { row ->
                Row {
                    repeat(columns) { col ->
                        val index = itemsPerPage * page + row * columns + col
                        if (index < items.size) {
                            Card(
                                colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                                //shape = CutCornerShape(1.dp),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .height(80.dp)
                                    .width(340.dp)
                            ) {
                                /*
                                Text(
                                    text = "Card #$index: ${items[index]}",
                                    modifier = Modifier.background(Color.White),
                                )

                                 */
                                AsyncImage(
                                    model = ImageRequest.Builder(context = LocalContext.current) //samplePhoto.imgSrc,
                                        .data(article.urlToImage)
                                        .crossfade(true)
                                        .build(),
                                    //error = painterResource(R.drawable.ic_broken_image),
                                    //placeholder = painterResource(R.drawable.loading_img),
                                    contentDescription = stringResource(R.string.news_thumbnail),
                                    contentScale = ContentScale.FillHeight,  // to cover whole screen
                                    modifier = Modifier
                                        //.align(Alignment.Center)
                                        .clip(MaterialTheme.shapes.small)
                                        .size(width = 80.dp, height = 80.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}