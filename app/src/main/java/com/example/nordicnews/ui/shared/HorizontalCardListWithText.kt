package com.example.nordicnews.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nordicnews.R
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.models.ArticleMockData


@Composable
fun CardWithImageAndText(article : Article, modifier : Modifier = Modifier) {

        Row(modifier = Modifier.wrapContentHeight(align = Alignment.Top)
        ) {
            Card(
                modifier = modifier.size(
                    width = 330.dp,
                    height = 80.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                )
            ) {
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(article.urlToImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.news_thumbnail),
                        contentScale = ContentScale.FillHeight,  // to cover whole screen
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .size(width = 80.dp, height = 80.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 7.dp, start = 18.dp, bottom = 7.dp)
                    ) {
                        Text(
                            text = article.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 22.sp,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(29, 27, 32),
                            modifier = Modifier.padding(
                                bottom = 5.dp
                            )
                        )
                        Row {
                            Text(
                                text = article.source.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight(400),
                                lineHeight = 17.41.sp,
                                color = Color(29, 27, 32),
                                modifier = Modifier.padding(
                                    end = 7.dp
                                )
                            )
                            Text(
                                text = "·",
                                fontSize = 14.sp,
                                fontWeight = FontWeight(400),
                                lineHeight = 17.41.sp,
                                color = Color(29, 27, 32),
                                modifier = Modifier.padding()
                            )
                            Text(
                                text = getOffset(article.publishedAt),
                                fontSize = 14.sp,
                                fontWeight = FontWeight(400),
                                lineHeight = 17.41.sp,
                                color = Color(29, 27, 32),
                                modifier = Modifier.padding(
                                    start = 7.dp
                                )
                            )
                        }
                    }
                }
            }
        }
}

@Composable
fun HorizontalCardListWithText(
    onItemClick: (Article) -> Unit,
    articles : List<Article>
) {
    Column {
        Text(
            text = "Focus On AI",
            fontWeight = FontWeight(700),
            fontSize = 20.sp,
            lineHeight = 28.sp,
            style = MaterialTheme.typography.headlineMedium,
            color = Color(29, 27, 32),
            modifier = Modifier.padding(
                start = 25.dp,
                end = 25.dp,
                bottom = 20.dp
            )
        )
        LazyHorizontalGrid(
            modifier = Modifier.height(180.dp).padding(start = 25.dp),
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(articles){ article ->
                CardWithImageAndText(
                    article = article,
                    modifier = Modifier
                        .clickable { onItemClick(article) })
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HorizontalCardListPreview() {
    HorizontalCardListWithText(
        onItemClick = {},
        articles = ArticleMockData.articleList)
}