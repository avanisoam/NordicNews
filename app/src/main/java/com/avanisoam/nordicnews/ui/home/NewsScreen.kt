package com.avanisoam.nordicnews.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.avanisoam.nordicnews.data.model.Article
import com.avanisoam.nordicnews.ui.shared.ArticleCard
import org.koin.androidx.compose.getViewModel

@Composable
fun PagingNews(
    articles : LazyPagingItems<Article>,
    onItemClick: (Article) -> Unit
) {
    //val viewModel: HomeViewModel = getViewModel<HomeViewModel>()

    //val articles = viewModel.getBreakingNews().collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(500.dp),
        //.background(Color.Red)
    ) {
        items(count = articles.itemCount) { index ->
            val item = articles[index]
            item?.let {
                ArticleCard(
                    article = it,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .clickable { onItemClick(it) }
                )
            }


            /*
            Text(
                text = "${item}",
                fontSize = 35.sp,
                color = Color.Black
            )
             */
            //Divider()
        }
        when {
            articles.loadState.refresh is LoadState.Loading ||
                    articles.loadState.append is LoadState.Loading -> {

                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Loading...")

                        // Material 3 Bug
                        /*
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                         */
                    }
                }
            }

            articles.loadState.refresh is LoadState.Error ||
                    articles.loadState.append is LoadState.Error -> {

                item {
                    //Text(text = "Error")
                }
            }

            articles.loadState.refresh is LoadState.NotLoading -> {

            }
        }
    }
}

@Composable
fun UserCard(user: Article) {
    Card(
        //elevation = 4.dp,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.urlToImage)
                    .crossfade(true)
                    .build(),
                //placeholder = painterResource(R.drawable.ic_avatar_placeholder),
                contentDescription = "stringResource(R.string.description)",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp)
            )

            Text(
                text = user.title,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
