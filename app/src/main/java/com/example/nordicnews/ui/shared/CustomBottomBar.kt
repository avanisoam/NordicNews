package com.example.nordicnews.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nordicnews.ui.bookmark.BookmarksDestination
import com.example.nordicnews.ui.home.HomeDestination
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.search.SearchDestination
import com.example.nordicnews.utils.popAndLaunchSingleTop

@Composable
fun CustomBottomBar(navController: NavController) {
    val screens = listOf(
        HomeDestination,
        SearchDestination,
        BookmarksDestination
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            .background(Color.Transparent)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun AddItem(
    screen: NavigationDestination,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    val background = Color.Red.copy(alpha = 0.6f).takeIf {
        selected
    } ?: Color.Transparent

    val contentColor = Color.White.takeIf {
        selected
    } ?: Color.Black

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = {
                    navController.navigate(screen.route) {
                        popAndLaunchSingleTop(navController)
                    }
                }
            )
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = screen.selectedIcon.takeIf {
                        selected
                    } ?: screen.unSelectedIcon
                ),
                contentDescription = "icon",
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = stringResource(id = screen.titleRes),
                    color = contentColor
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun BottomNavPreview() {
    CustomBottomBar(
        navController = NavHostController(LocalContext.current)
    )
}
