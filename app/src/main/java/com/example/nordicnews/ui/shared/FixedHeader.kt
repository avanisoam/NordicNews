package com.example.nordicnews.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nordicnews.data.models.Article

@Composable
//fun LazyListScope.FixedHeader(article : Article,
fun FixedHeader(article : Article,
                modifier : Modifier = Modifier
) {
    //LazyColumn(modifier.height(500.dp)) {
    Column(modifier.height(390.dp)){//390 -height
        //item{
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(article.urlToImage)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                //.width(390.dp)
                .height(220.dp),
            //.clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        //Spacer(modifier = Modifier.height(4.dp))
        /*
        Text(
            text = buildAnnotatedString{
                append(article.source.name)
                append(".")
                append("10 mins")
            },
            fontSize = 14.sp,
            lineHeight = 17.41.sp,
            fontWeight = FontWeight(400),
            modifier = Modifier.padding(
                top = 20.dp,
                bottom = 9.dp,
                start = 25.dp,
                end = 25.dp
            )
        )

         */
        Row(modifier = Modifier.padding(
            start = 25.dp,
            end=25.dp,
            bottom = 9.dp,
            top = 20.dp
        )){
            Text(
                text = article.source.name,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                lineHeight = 17.41.sp,
                color = Color(29, 27, 32),
                style = MaterialTheme.typography.labelLarge,
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
                modifier = Modifier.padding(
                    //start = 0.5.dp,
                    //end = 0.5.dp
                )
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

        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                top=9.dp
            ),
        )
        // }
    }
}