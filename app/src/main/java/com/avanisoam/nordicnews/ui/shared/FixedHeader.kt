package com.avanisoam.nordicnews.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.avanisoam.nordicnews.R
import com.avanisoam.nordicnews.data.model.Article

@Composable
fun FixedHeader(
    article : Article,
    onItemClick : (Article) -> Unit,
    modifier : Modifier = Modifier
) {
    Column(modifier.height(390.dp)) {

        AsyncImage(
            model = ImageRequest.Builder(
                context = LocalContext.current
            ).data(article.urlToImage).build(),
            contentDescription = "Fixed Header Async Image",
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clickable { onItemClick(article) },
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.padding(
                start = 10.dp,
                end=25.dp,
                bottom = 9.dp,
                top = 20.dp
            )
        ) {
            Text(
                text = article.source.name,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                lineHeight = 17.41.sp,
                color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
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
                color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
                modifier = Modifier.padding()
            )
            Text(
                text = getOffset(article.publishedAt),
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                lineHeight = 17.41.sp,
                color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
                modifier = Modifier.padding(
                    start = 7.dp
                )
            )
        }

        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,//Color.Black,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            modifier = Modifier.padding(
                start = 10.dp,
                end = 20.dp,
                top=9.dp
            )
            .clickable { onItemClick(article) }
        )
    }
}