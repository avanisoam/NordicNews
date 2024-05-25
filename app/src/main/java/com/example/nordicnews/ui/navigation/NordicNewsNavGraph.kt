package com.example.nordicnews.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
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
import com.google.gson.Gson

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
        composable(HomeDestination.route) { HomeScreen(
            navigateToDetailScreen = {
                val json = Uri.encode(Gson().toJson(it))
                navController.navigate("detail/$json")
            },
            navigateToSearchScreen = {navController.navigate("search/$it")},
            navController) }
        //composable(DetailDestination.route) { DetailScreen(navController)}

        composable(
            DetailDestination.route,//"detail/{article}",
            arguments = listOf(
                navArgument("article") {
                    type = AssetParamType()
                }
            )
        ) {
            val article = it.arguments?.getParcelable<Article>("article")

            DetailScreen(
                navigateToDetailScreen = {val json = Uri.encode(Gson().toJson(it))
                    navController.navigate("detail/$json")},
                article = article,
                navController= navController,
                navigateUp = {navController.navigateUp()},
            )
        }

        composable(route = SearchDestination.route) {
            SearchScreen(
                navigateToDetailScreen = { val json = Uri.encode(Gson().toJson(it))
                    navController.navigate("detail/$json")},
                navController
            )
        }
        composable(route = SearchDestination.routeWithArgs,
            arguments = listOf(navArgument(SearchDestination.categoryArg) {
                type = NavType.StringType
            })) {
            SearchScreen(
                navigateToDetailScreen = { val json = Uri.encode(Gson().toJson(it))
                    navController.navigate("detail/$json")},
                navController
            )
        }


        composable(BookmarksDestination.route) {
            BookmarksScreen(
                navigateToDetailScreen = {
                    val json = Uri.encode(Gson().toJson(it))
                    navController.navigate("detail/$json")
                },
                navController)
        }
    }
}