package com.example.nordicnews.ui.shared

import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nordicnews.ui.bookmark.BookmarksDestination
import com.example.nordicnews.ui.home.HomeDestination
import com.example.nordicnews.ui.search.SearchDestination


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

@Preview
@Composable
private fun BottomNavigationBarPreview() {
    BottomNavigationBar(
        navController = NavHostController(LocalContext.current)
    )
}