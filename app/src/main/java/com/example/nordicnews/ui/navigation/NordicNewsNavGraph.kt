package com.example.nordicnews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nordicnews.ui.bookmark.BookmarksDestination
import com.example.nordicnews.ui.bookmark.BookmarksScreen
import com.example.nordicnews.ui.detail.DetailDestination
import com.example.nordicnews.ui.detail.DetailScreen
import com.example.nordicnews.ui.home.HomeDestination
import com.example.nordicnews.ui.home.HomeScreen
import com.example.nordicnews.ui.search.SearchDestination
import com.example.nordicnews.ui.search.SearchScreen

@Composable
fun NordicNewsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(HomeDestination.route) { HomeScreen(navController) }
        composable(DetailDestination.route) { DetailScreen(navController)}
        composable(SearchDestination.route) { SearchScreen(navController) }
        composable(BookmarksDestination.route) { BookmarksScreen(navController)}
    }
}