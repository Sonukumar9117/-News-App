package com.loc.newsapp.presentation.onboarding.search

import androidx.paging.PagingData
import com.loc.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
) {
//    fun copy(searchQuery: String = this.searchQuery): SearchState {
//        return SearchState(
//            searchQuery = searchQuery,
//            articles = this.articles
//        )
//    }
}
