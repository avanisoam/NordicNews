package com.avanisoam.nordicnews

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.avanisoam.nordicnews.ui.navigation.NordicNewsNavHost

@Composable
fun NordicNewsApp(
    navController: NavHostController = rememberNavController()
) {
    NordicNewsNavHost(navController = navController)
}