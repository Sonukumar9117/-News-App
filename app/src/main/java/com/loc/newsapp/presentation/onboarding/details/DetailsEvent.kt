package com.loc.newsapp.presentation.onboarding.details

sealed class DetailsEvent {
    object SavedArticle : DetailsEvent()
}