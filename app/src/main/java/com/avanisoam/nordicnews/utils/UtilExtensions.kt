package com.avanisoam.nordicnews.utils

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.popAndLaunchSingleTop(navController: NavController) {
    popUpTo(navController.graph.startDestinationId)
    launchSingleTop = true
}