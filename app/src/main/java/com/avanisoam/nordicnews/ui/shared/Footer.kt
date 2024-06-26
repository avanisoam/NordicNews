package com.avanisoam.nordicnews.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.avanisoam.nordicnews.R
import com.avanisoam.nordicnews.ui.bookmark.BookmarksDestination
import com.avanisoam.nordicnews.ui.home.HomeDestination
import com.avanisoam.nordicnews.ui.navigation.NavigationDestination
import com.avanisoam.nordicnews.ui.search.SearchDestination
import com.avanisoam.nordicnews.utils.popAndLaunchSingleTop

@Composable
fun Footer(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfff7faff)),
        //horizontalArrangement = Arrangement.spacedBy(80.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        FooterItem(
            screen = HomeDestination,
            currentDestination = currentDestination,
            navController = navController
        )
        Spacer(modifier = Modifier.width(47.dp))

        FooterItem(
            screen = SearchDestination,
            currentDestination = currentDestination,
            navController = navController
        )

        Spacer(modifier = Modifier.width(47.dp))

        FooterItem(
            screen = BookmarksDestination,
            currentDestination = currentDestination,
            navController = navController
        )
    }
}

@Composable
fun FooterItem(
    screen: NavigationDestination,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    Box(
        modifier = Modifier
            .height(70.dp)
            .clickable(
                onClick = {
                    navController.navigate(screen.route) {
                        popAndLaunchSingleTop(navController)
                    }
                }
            )
            .padding(top = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = screen.selectedIcon).takeIf {
                    selected
                } ?: painterResource(id = screen.unSelectedIcon),
                contentDescription = stringResource(
                    id = screen.titleRes
                ),
                //modifier = Modifier.padding()
            )
            Text(
                text = stringResource(
                    id = screen.titleRes
                ),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(bottom = 11.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color(61, 138, 255).takeIf {
                    selected
                } ?: Color(29, 27, 32)
            )
            if (selected) {
                Image(
                    painter = painterResource(
                        id = R.drawable.indicator
                    ),
                    contentDescription = "indicator"
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FooterPreview() {
    Footer(
        navController = NavController(LocalContext.current)
    )
}