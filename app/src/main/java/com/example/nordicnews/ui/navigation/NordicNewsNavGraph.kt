package com.example.nordicnews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nordicnews.data.models.Article
import com.example.nordicnews.data.models.AssetParamType
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
        //composable(DetailDestination.route) { DetailScreen(navController)}

        composable(
            "detail/{article}",
            arguments = listOf(
                navArgument("article") {
                    type = AssetParamType()
                }
            )
        ) {
            val article = it.arguments?.getParcelable<Article>("article")

            DetailScreen(
                article = article,
                navController= navController,
                // TODO:
                //navigateBack = {navController.popBackStack()},
                //togglePreference = {detailViewModel.saveBookmark(it)}
            )
        }

        composable(SearchDestination.route) { SearchScreen(navController) }
        composable(BookmarksDestination.route) { BookmarksScreen(navController)}
    }
}