package com.example.nordicnews.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nordicnews.R
import com.example.nordicnews.data.models.Article
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePickerDefaults
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nordicnews.data.models.ArticleMockData
import com.example.nordicnews.data.models.Source
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.Date
import java.util.TimeZone


@Composable
fun ArticleApp() {
    ArticleList(articles = ArticleMockData.articleList)
}

@Composable
fun ArticleList(articles : List<Article>,modifier : Modifier = Modifier) {
    LazyColumn {
        items(articles){article ->
            ArticleCard(
                article = article,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun ArticleCard(article : Article,modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .fillMaxWidth()
        .size(80.dp)) {
        Row(modifier = Modifier
            .padding(2.dp),
        )
            {
            //Box(modifier = modifier){
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
                /*
            Image(
                painter = painterResource(article.urlToImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(width = 80.dp, height = 80.dp),
                )
                 */
            //}
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) {
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

                Row(modifier = Modifier.padding(top= 4.dp)) {
                    Text(
                        text = article.source.name,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(
                            //start = 8.dp,
                            //bottom = 4.dp
                        )
                    )
                    Text(text = ".",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = 5.dp,
                            //bottom = 4.dp
                        )
                    )
                    Text(text = getOffset(article.publishedAt),
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

fun getOffset(input: String) : String {
    // example input, some future datetime
    //val input = "2022-12-24T13:22:51.837Z"
    // parse that future datetime
    val offsetDateTime = OffsetDateTime.parse(input)
    // build up a duration between the input and now, use the same class
    //val duration = Duration.between(OffsetDateTime.now(), offsetDateTime)
    val duration = Duration.between(offsetDateTime, OffsetDateTime.now())
    // get the difference in full days
    var temp = ""
    var year : Long = 0
    var days : Long = 0
    var month : Long = 0
    var hours : Long = 0
    var min : Long = 0

    min = duration.toMinutes()
    temp = "$min min"
    if(min > 59)
    {
        hours = duration.toHours()
        temp = "$hours hrs"
    }
    if(hours > 59)
    {
        days = duration.toDays()
        temp = "$days days"
    }
    if(days > 30)
    {
        temp = "more then month"
    }
    if(days > 364)
    {
        temp = "more then year"
    }

    // print the result as "days left"
    //println("$days days left")
    return temp.toString()
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


@Preview
@Composable
private fun ArticleCardPreview1() {
    ArticleApp()
}

