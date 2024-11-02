package com.loc.newsapp.presentation.onboarding.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.presentation.onboarding.OnBoardingViewModel
import com.loc.newsapp.presentation.onboarding.bookmark.BookmarkScreen
import com.loc.newsapp.presentation.onboarding.bookmark.BookmarkViewModel
import com.loc.newsapp.presentation.onboarding.home.HomeScreen
import com.loc.newsapp.presentation.onboarding.home.HomeViewModel
import com.loc.newsapp.presentation.onboarding.onBoardingScreen
import com.loc.newsapp.presentation.onboarding.search.SearchEvent
import com.loc.newsapp.presentation.onboarding.search.SearchScreen
import com.loc.newsapp.presentation.onboarding.search.SearchViewModel

@Composable
fun NavGraph(
    startDestination:String
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination=Route.OnBoardingScreen.route
        ){
            composable(route = Route.OnBoardingScreen.route){
                val viewModel : OnBoardingViewModel = hiltViewModel()
                onBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ){
            composable(route = Route.NewsNavigatorScreen.route){
                val viewModel:BookmarkViewModel = hiltViewModel()
                BookmarkScreen(state =viewModel.state.value, navigate = {} )
            }
        }
    }
}