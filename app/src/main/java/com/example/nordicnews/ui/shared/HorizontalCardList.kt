package com.example.nordicnews.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nordicnews.data.models.Category

@Composable
fun CardShower(
    categoryName: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color
) {
    Row(modifier = Modifier.wrapContentHeight(align = Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Card(
            modifier = modifier.size(100.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            )
        ) {
            Column {
                Spacer(modifier = Modifier.weight(2f))
                Text(
                    text = categoryName,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(500),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(
                        bottom =12.dp,
                        start = 10.dp
                    ),
                    color = textColor
                )
            }
        }
    }
}

@Composable
fun ColorfulTabsList(
    category : List<Category>,
    onCardClick: (String) -> Unit,
    ) {
    val listOfColors = listOf(
        Color(61, 138, 255),
        Color(255, 47, 33),
        Color(56, 168, 82),
        Color(255, 187, 0)
    )
    Column {
        Text(
            text = "Topics",
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
            modifier = Modifier
                .height(100.dp)
                .padding(start = 25.dp),
            rows = GridCells.Fixed(1)
        ) {
            itemsIndexed(category) { i, item ->
                CardShower(
                    categoryName = item.category,
                    backgroundColor = listOfColors[i].copy(0.2f),
                    textColor = listOfColors[i],
                    modifier = Modifier.padding(4.dp)
                        .clickable { onCardClick(item.category) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ColorfulTabsListPreview() {}