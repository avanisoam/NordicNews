package com.avanisoam.nordicnews.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.avanisoam.nordicnews.R
import com.avanisoam.nordicnews.data.model.Article
import com.avanisoam.nordicnews.data.model.ArticleMockData
import com.avanisoam.nordicnews.data.model.Source
import java.time.Duration
import java.time.OffsetDateTime


@Composable
fun ArticleApp() {
    ArticleList(articles = ArticleMockData.articleList, onItemClick = {})
}

@Composable
fun ArticleList(
    onItemClick: (Article) -> Unit,
    articles: List<Article>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.height(500.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(articles) { article ->
            ArticleCard(
                article = article,
                modifier = Modifier
                    .clickable { onItemClick(article) }
            )
        }
    }
}

@Composable
fun ArticleListV1(
    onItemClick: (Article) -> Unit = {},
    articles: List<Article>
) {
    articles.forEach { article ->
        ArticleCard(
            article = article,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .clickable { onItemClick(article) }
        )
    }
}

@Composable
fun ArticleCard(article : Article,modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(80.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row{
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.news_thumbnail),
                contentScale = ContentScale.FillHeight,  // to cover whole screen
                modifier = Modifier
                    //.align(Alignment.Center)
                    .clip(MaterialTheme.shapes.small)
                    .size(width = 80.dp, height = 80.dp),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp, start = 18.dp, bottom = 7.dp,end = 18.dp)
            ) {
                Text(
                    text = article.title?: "",
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
                Row{
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

fun getOffset(input: String) : String {
    // example input, some future datetime
    //val input = "2022-12-24T13:22:51.837Z"
    // parse that future datetime
    val offsetDateTime = OffsetDateTime.parse(input)
    // build up a duration between the input and now, use the same class
    //val duration = Duration.between(OffsetDateTime.now(), offsetDateTime)
    val duration = Duration.between(offsetDateTime, OffsetDateTime.now())
    // get the difference in full days
    val days : Long = duration.toDays()
    val hours : Long = duration.toHours()
    val min = duration.toMinutes()

    // print the result as "days left"
    //println("$days days left")
    return when {
        min > 59 && hours < 59 -> {
            "$hours hrs"
        }
        hours > 59 && days < 31 -> {
            "$days days"
        }
        days in 31..363 -> {
            "more than month"
        }
        days > 364 -> {
            "more than year"
        }
        else -> "$min min"
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

@Preview
@Composable
private fun ArticleCardPreview() {
    ArticleCard(
        Article(
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
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/A44D/production/_116316024_breaking-promo-v20e2-red-976x549.png", //R.drawable.bbc_breakingnews
        )
    )
}


@Preview(showSystemUi = true)
@Composable
private fun ArticleCardPreview1() {
    ArticleApp()
}

@Preview(showSystemUi = true)
@Composable
private fun ArticleCardPreview2() {
    ArticleListV1(
        articles = ArticleMockData.articleList
    )
}

