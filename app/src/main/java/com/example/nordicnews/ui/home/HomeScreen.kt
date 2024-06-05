package com.example.nordicnews.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nordicnews.R
import com.example.nordicnews.data.model.Article
import com.example.nordicnews.data.model.ArticleMockData
import com.example.nordicnews.data.model.Category
import com.example.nordicnews.ui.developerOptions.DeveloperOptionsDestination
import com.example.nordicnews.ui.navigation.NavigationDestination
import com.example.nordicnews.ui.settings.SettingsDestination
import com.example.nordicnews.ui.shared.ArticleListV1
import com.example.nordicnews.ui.shared.ColorfulTabsList
import com.example.nordicnews.ui.shared.ErrorScreen
import com.example.nordicnews.ui.shared.FixedHeader
import com.example.nordicnews.ui.shared.Footer
import com.example.nordicnews.ui.shared.HorizontalCardListWithText
import com.example.nordicnews.ui.shared.LoadingScreen
import com.example.nordicnews.ui.theme.NordicNewsTheme
import kotlinx.coroutines.launch


object HomeDestination : NavigationDestination {

    override val route = "home"

    override val titleRes = R.string.home

    override val selectedIcon = R.drawable.home_selected_2
    override val unSelectedIcon = R.drawable.home_2

}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val destinationRoute: String?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToDetailScreen : (Article) -> Unit = {},
    navigateToSearchScreen : (String) -> Unit = {},
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val categoryList by viewModel.categoryUIState.collectAsState()

    val isLiteMode: Boolean by viewModel.isLiteDisplayMode.collectAsState()
    Log.e("MODE", "isLiteMode: $isLiteMode")

    val drawerItems = listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            destinationRoute = null
        ),
        NavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            destinationRoute = SettingsDestination.route
        ),
        NavigationItem(
            title = "Developer Options",
            selectedIcon = Icons.Filled.Build,
            unselectedIcon = Icons.Outlined.Build,
            destinationRoute = DeveloperOptionsDestination.route
        ),
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp))
                    drawerItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 16.sp,
                                    style = MaterialTheme.typography.headlineSmall,
                                    lineHeight = 22.sp,
                                    textAlign = TextAlign.Center
                                )
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index

                                if (item.destinationRoute == null) {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                } else {
                                    navController.navigate(item.destinationRoute)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.selectedIcon.takeIf {
                                        index == selectedItemIndex
                                    } ?: item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier.padding(
                                NavigationDrawerItemDefaults.ItemPadding
                            )
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Home",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.headlineSmall,
                                lineHeight = 22.sp,
                                textAlign = TextAlign.Center
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        containerColor = Color.White
                    ) {
                        Footer(navController)
                    }
                }
            ) { innerPadding ->

                when(uiState) {
                    is HomeUiState.Loading -> LoadingScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                    is HomeUiState.Success -> Result(
                        categoryList = categoryList,
                        navigateToSearchScreen = navigateToSearchScreen,
                        navigateToDetailScreen = navigateToDetailScreen,
                        isLiteMode = isLiteMode,
                        articles = (uiState as HomeUiState.Success).articleList,
                        modifier = Modifier.padding(innerPadding)
                    )
                    is HomeUiState.Error -> ErrorScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun Result(
    categoryList : List<Category>,
    navigateToSearchScreen : (String) -> Unit,
    navigateToDetailScreen : (Article) -> Unit,
    isLiteMode : Boolean,
    articles : List<Article>,
    modifier : Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
    ) {
        if (isLiteMode.not()) {
            item {
                FixedHeader(
                    article = ArticleMockData.articleList[13],
                    onItemClick = {
                        navigateToDetailScreen(it)
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(50.dp)) }
            item {
                HorizontalCardListWithText(
                    onItemClick = { navigateToDetailScreen(it) },
                    articles = articles.take(20)
                )
            }

            item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                ColorfulTabsList(
                    category = categoryList,
                    onCardClick = { navigateToSearchScreen(it) }
                )
            }

            item { Spacer(modifier = Modifier.height(50.dp)) }
        }
        item{
            Column(modifier = Modifier.padding(
                start = 10.dp,
                end = 10.dp,
                bottom = 50.dp
            )) {
                Text(
                    text = "All Articles",
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,//Color(29, 27, 32),
                    modifier = Modifier.padding(
                        bottom = 20.dp
                    )
                )

                ArticleListV1(
                    onItemClick = { navigateToDetailScreen(it) },
                    // Data from Api
                    articles = articles,
                )
            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    NordicNewsTheme {
        HomeScreen(
            navController = rememberNavController()
        )
    }
}