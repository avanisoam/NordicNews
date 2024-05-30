package com.example.nordicnews.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nordicnews.R
import com.example.nordicnews.ui.bookmark.BookmarksDestination
import com.example.nordicnews.ui.home.HomeDestination
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.search.SearchDestination

object BottomBar {
    val Items = listOf(
        HomeDestination,
        SearchDestination,
        BookmarksDestination,
    )
}

@Composable
fun Footer(
    navController: NavController,
    modifier : Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(
            //top = 10.dp,
            //start = 57.dp,
            //end = 58.dp
        )
        .background(Color(0xfff7faff)),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        BottomBar.Items.forEach{item ->
            FooterItem(
                screen = item,
                currentDestination = currentDestination ,
                navController = navController)

        }
    }
}

@Composable
fun FooterItem(
    screen: NavigationDestination,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    Box(
        modifier = Modifier
            .height(70.dp)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
            .padding(top = 10.dp)
    ) {
        Column(modifier = Modifier.padding(start=25.dp,end=25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter =
                if (selected)
                    painterResource(id = screen.selectedIcon)
                else
                    painterResource(id = screen.unSelectedIcon)
                ,
                contentDescription = stringResource(id = screen.titleRes),
                modifier = Modifier.padding(
                    //start = 9.dp,
                    //end = 9.dp
                )
            )
            Text(
                text = stringResource(id = screen.titleRes),
                //fontWeight = FontWeight(500),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(bottom = 11.dp)
                    .align(Alignment.CenterHorizontally),
                color = if(selected) Color(61, 138, 255) else Color(29, 27, 32)
            )
            if(selected) {
                Image(
                    painter = painterResource(id = R.drawable.indicator),
                    contentDescription = "indicator",
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FooterPreview() {
    Footer(navController = NavController(LocalContext.current))
}