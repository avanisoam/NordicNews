package com.example.nordicnews

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nordicnews.ui.navigation.NordicNewsNavHost

@Composable
fun NordicNewsApp(
    navController: NavHostController = rememberNavController()
) {
    NordicNewsNavHost(navController = navController)
}