package com.example.nordicnews.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationDestination {
    /**
     * Unique name to define the path for a composable
     */
    val route: String

    /**
     * String resource id to that contains title to be displayed for the screen.
     */
    val titleRes: Int

    // Icon
    val selectedIcon : Int

    val unSelectedIcon : Int

}