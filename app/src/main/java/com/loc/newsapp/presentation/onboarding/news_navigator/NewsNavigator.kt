package com.loc.newsapp.presentation.onboarding.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.R
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.onboarding.bookmark.BookmarkScreen
import com.loc.newsapp.presentation.onboarding.bookmark.BookmarkViewModel
import com.loc.newsapp.presentation.onboarding.details.DetailsEvent
import com.loc.newsapp.presentation.onboarding.details.DetailsScreen
import com.loc.newsapp.presentation.onboarding.details.DetailsViewModel
import com.loc.newsapp.presentation.onboarding.home.HomeScreen
import com.loc.newsapp.presentation.onboarding.home.HomeViewModel
import com.loc.newsapp.presentation.onboarding.navgraph.Route
import com.loc.newsapp.presentation.onboarding.news_navigator.components.BottomNavigationItem
import com.loc.newsapp.presentation.onboarding.news_navigator.components.NewsBottomNavigation
import com.loc.newsapp.presentation.onboarding.search.SearchScreen
import com.loc.newsapp.presentation.onboarding.search.SearchViewModel

@Composable
fun NewsNavigator(){
    val bottomNavigationItems = remember{
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by remember {
        mutableStateOf(0)
    }
    selectedItem = remember(key1 = backStackState) {
         when(backStackState?.destination?.route){
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route || backStackState?.destination?.route == Route.SearchScreen.route || backStackState?.destination?.route == Route.BookmarkScreen.route
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(navController, Route.HomeScreen.route)
                            1 -> navigateToTab(navController, Route.SearchScreen.route)
                            2 -> navigateToTab(navController, Route.BookmarkScreen.route)
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(navController = navController, startDestination = Route.HomeScreen.route, modifier = Modifier.padding(bottom = bottomPadding)){
            composable(route = Route.HomeScreen.route){
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(navController=navController,route = Route.SearchScreen.route )
                    },
                    navigateToDetails = { article->
                        navigateToDetails(navController = navController, article = article)
                    }
                )
            }
            composable(route = Route.SearchScreen.route){
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(state = state, event = viewModel::onEvent, navigateToDetails = {navigateToDetails(navController=navController, article = it)})
            }
            composable(route = Route.DetailsScreen.route){
                val viewModel: DetailsViewModel = hiltViewModel()
                //TODO: Handle side effect
                if(viewModel.sideEffect!=null){
                    Toast.makeText(LocalContext.current,viewModel.sideEffect,Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let {article ->
                    DetailsScreen(article =article,
                        event = viewModel ::onEvent,
                        navigateUp = {navController.navigateUp()}
                    )
                }
            }
            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(state = state, navigateToDetails = { article->
                    navigateToDetails(navController=navController,article=article)
                })
            }
        }
    }
}

private fun navigateToDetails(navController: NavHostController, article: Article) {
     navController.currentBackStackEntry?.savedStateHandle?.set("article",article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )

}

private fun navigateToTab(navController: NavController,route:String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let {
            popUpTo(it){
                saveState = true
            }
            restoreState = true
            launchSingleTop = true

        }
    }
}