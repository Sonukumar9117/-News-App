package com.loc.newsapp.presentation.onboarding.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.onboarding.Dimens.MediumPadding1
import com.loc.newsapp.presentation.onboarding.common.ArticlesList
import com.loc.newsapp.presentation.onboarding.common.SearchBar
import com.loc.newsapp.presentation.onboarding.navgraph.Route

@Composable
fun SearchScreen(
    state: SearchState,
    event:(SearchEvent) -> Unit,
    navigate:(String) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)
            .statusBarsPadding()
    ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = {
                event(SearchEvent.SearchNews)
            }
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(
                articles = articles,
                onClick = {
                    //TODO: Navigate to details screen
                    navigate(Route.DetailsScreen.route)
                }
            )
        }
    }
}