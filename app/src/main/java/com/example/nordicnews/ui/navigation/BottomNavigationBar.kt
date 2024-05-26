package com.example.nordicnews.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nordicnews.ui.bookmark.BookmarksDestination
import com.example.nordicnews.ui.home.HomeDestination
import com.example.nordicnews.ui.search.SearchDestination


object BottomBar {
    val Items = listOf(
        HomeDestination,
        SearchDestination,
        BookmarksDestination,
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomBar.Items.forEach() { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    if(currentRoute == item.route)
                        Icon(painter = painterResource(id = item.selectedIcon), contentDescription = null)
                    else
                        Icon(painter = painterResource(id = item.unSelectedIcon), contentDescription = null)
                },
                label = {
                    if(currentRoute == item.route)
                    {
                        Text(
                            modifier = Modifier
                                .drawBehind {
                                    val strokeWidthPx = 1.dp.toPx()
                                    val verticleOffset = size.height - 2.sp.toPx()
                                    drawLine(
                                        // color = Color(61, 138, 255),
                                        color = Color.Yellow,
                                        strokeWidth = strokeWidthPx,
                                        start = Offset(0f, verticleOffset),
                                        end = Offset(size.width,verticleOffset)
                                    )
                                },
                            text = stringResource(item.titleRes)
                        )
                        //Spacer(modifier = Modifier.height(8.dp))
                    }
                    else
                    {
                        Text(text = stringResource(item.titleRes))
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(61, 138, 255),
                    selectedTextColor = Color(61, 138, 255),
                    indicatorColor = Color.White//MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    }
}