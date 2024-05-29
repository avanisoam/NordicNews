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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.models.Category

@Composable
fun CardShower(categoryName: String, modifier: Modifier= Modifier,backgroundColor : Color,textColor : Color) {
    Row(modifier = Modifier.wrapContentHeight(align = Alignment.Top),
        //.border(BorderStroke(2.dp, SolidColor(Color.Red))),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Card(
            modifier = modifier
                .size(100.dp)
            ,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
            ),
        ) {
                Column {
                    Spacer(modifier = Modifier.weight(2f))

                Text(
                        text = categoryName,
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(500),
                        style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(
                            //top = 66.dp,
                            bottom =12.dp,
                            start = 10.dp,
                            //end = 69.dp
                        ),
                        color = textColor
                    )
            }
        }
    }
}

@Composable
fun ColorfulTabsList(
    modifier : Modifier = Modifier,
    category : List<Category>,
    onCardClick: (String) -> Unit,
    ) {
    val listOfColorForBackground =
        listOf(
            Color(226, 237, 255),
            Color(255, 224, 222),
            Color(225, 242, 229),
            Color(255, 245, 217)
        )
    val listOfColorForText =
        listOf(
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
            //itemsIndexed(listOfItems) { i, item ->
            itemsIndexed(category) { i, item ->
                CardShower(
                    categoryName = item.category,
                    backgroundColor = listOfColorForBackground[i],
                    textColor = listOfColorForText[i],
                    modifier = Modifier.padding(4.dp)
                        .clickable { onCardClick(item.category) }
                )
            }
        }
    }
    
}

@Preview
@Composable
private fun ColorfulTabsListPreview() {
    //ColorfulTabsList(category = List<Category>(3))
}