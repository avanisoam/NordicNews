package com.example.nordicnews.ui.shared

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

@Composable
fun HorizontalCardList() {
    
}

@Composable
fun CardShower(beachName: String, modifier: Modifier= Modifier,backgroundColor : Color,textColor : Color) {
    val listOfColor =
        listOf(
            Color(226, 237, 255),
            Color(255, 224, 222),
            Color(225, 242, 229),
            Color(255, 245, 217)
        )

    Row(modifier = Modifier.wrapContentHeight(align = Alignment.Top),
        //.size(100.dp,100.dp)
        //.border(BorderStroke(2.dp, SolidColor(Color.Red))),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Card(
            modifier = modifier
                //.height(80.dp)
                //.padding(8.dp)
                //.sizeIn(minWidth = 100.dp, minHeight = 100.dp)
                .size(100.dp)
            ,
            //.background(listOfColor.random()),
            /*
            colors = CardDefaults.cardColors(
                containerColor = listOfColor.random(),

             */
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
            ),
            //elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            //shape = RoundedCornerShape(50.dp)
        ) {

            /*
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = beachName,
                modifier = Modifier.padding(bottom = 4.dp),
                colorFilter = ColorFilter.tint(color = listOfColor.random())
           )
             */
            //Text(text = beachName)
                Column {
                    Spacer(modifier = Modifier.weight(2f))

                Text(
                        text = beachName,
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(500),
                        style = MaterialTheme.typography.headlineSmall,
                        //textAlign = TextAlign.Left,
                    modifier = Modifier
                        //.fillMaxHeight()
                        .padding(
                            //top = 66.dp,
                            bottom =12.dp,
                            start = 15.dp,
                            //end = 69.dp
                        ),
                        //.border(BorderStroke(2.dp,Color.Red)),
                        // add custom modifier in text for text alignment
                            //.border(BorderStroke(2.dp, Color.Red))
                            //.align(Alignment.BottomStart),
                        color = textColor
                    )
                //}
            }
        }
    }
}

@Composable
fun ColorfulTabsList(modifier : Modifier = Modifier) {
    val listOfItems = mutableListOf(
       "AI","Search","Google","Gold"
    )
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
            itemsIndexed(listOfItems) { i, item ->
                CardShower(
                    beachName = item,
                    modifier = Modifier.padding(4.dp),
                    backgroundColor = listOfColorForBackground[i],
                    textColor = listOfColorForText[i]
                )
            }
        }
    }
    
}

@Preview
@Composable
private fun ColorfulTabsListPreview() {
    ColorfulTabsList()
}