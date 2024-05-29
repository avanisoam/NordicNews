package com.example.nordicnews.ui.navigation


interface NavigationDestination {
    /**
     * Unique name to define the path for a composable
     */
    val route: String

    /**
     * String resource id to that contains title to be displayed for the screen.
     */
    val titleRes: Int

    /**
     * Drawable resource id to that contains icons to be displayed for the screen.
     */
    val selectedIcon : Int

    val unSelectedIcon : Int

}