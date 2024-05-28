package com.example.nordicnews.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.nordicnews.ui.developerOptions.DeveloperOptionsDestination
import com.example.nordicnews.ui.developerOptions.DeveloperOptionsScreen
import com.example.nordicnews.ui.home.HomeDestination
import com.example.nordicnews.ui.home.HomeScreen
import com.example.nordicnews.ui.search.SearchDestination
import com.example.nordicnews.ui.search.SearchScreen
import com.example.nordicnews.ui.settings.SettingsDestination
import com.example.nordicnews.ui.settings.SettingsScreen
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

        // Home Screen Route
        composable(HomeDestination.route) {
            HomeScreen(
                navigateToDetailScreen = {
                    val json = Uri.encode(Gson().toJson(it))
                    navController.navigate("${DetailDestination.route}/$json")
                },
                navigateToSearchScreen = {
                    navController.navigate("${SearchDestination.route}/$it")
                },
                navController
            )
        }

        // Detail Screen Route
        composable(
            DetailDestination.routeWithArgs,
            arguments = listOf(
                navArgument(DetailDestination.ARTICLE_ARG) {
                    type = AssetParamType()
                }
            )
        ) { entry ->
            val article = entry.arguments?.getParcelable<Article>(DetailDestination.ARTICLE_ARG)

            DetailScreen(
                navigateToDetailScreen = {
                    val json = Uri.encode(Gson().toJson(it))
                    navController.navigate("${DetailDestination.route}/$json")
                },
                article = article,
                navController= navController,
                navigateUp = { navController.navigateUp() },
            )
        }

        // Search Screen Route 1
        composable(route = SearchDestination.route) {
            SearchScreen(
                navigateToDetailScreen = {
                    val json = Uri.encode(Gson().toJson(it))
                    navController.navigate("${DetailDestination.route}/$json")
                },
                navController
            )
        }
        // Search Screen Route 2
        composable(
            route = SearchDestination.routeWithArgs,
            arguments = listOf(
                navArgument(SearchDestination.CATEGORY_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            SearchScreen(
                navigateToDetailScreen = {
                    val json = Uri.encode(Gson().toJson(it))
                    navController.navigate("${DetailDestination.route}/$json")
                },
                navController
            )
        }

        // Bookmarks Screen Route
        composable(BookmarksDestination.route) {
            BookmarksScreen(
                navigateToDetailScreen = {
                    val json = Uri.encode(Gson().toJson(it))
                    navController.navigate("${DetailDestination.route}/$json")
                },
                navController
            )
        }

        // Settings Screen Route
        composable(SettingsDestination.route) {
            SettingsScreen(
                navController = navController,
                navigateUp = {navController.navigateUp()},
            )
        }

        // DeveloperOptions Screen Route
        composable(DeveloperOptionsDestination.route) {
            DeveloperOptionsScreen(
                navController = navController,
                navigateUp = {navController.navigateUp()},
            )
        }
    }
}