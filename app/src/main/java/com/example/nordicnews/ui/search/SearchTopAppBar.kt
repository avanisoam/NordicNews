package com.example.nordicnews.ui.search

import androidx.compose.runtime.Composable

@Composable
fun SearchTopAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when(searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            ClosedAppBar(
                onSearchIconClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            OpenSearchBar(
                text = searchTextState ,
                onTextChange = onTextChange ,
                onSearchClicked = {onSearchClicked(it)},
                onCloseClicked = onCloseClicked
            )
        }
    }
}