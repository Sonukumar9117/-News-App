package com.loc.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.loc.newsapp.R

data class Page (
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)
val pages = listOf(
    Page(
        title = "Discover New Experiences",
        description = "Explore a variety of exciting options tailored just for you. Our app brings personalized content to make each session unique and engaging.",
        image = R.drawable.onboarding1,
    ),
    Page(
        title = "Stay Connected",
        description = "Never miss out on what's happening. Stay up-to-date with real-time notifications and stay connected with the community and latest trends.",
        image = R.drawable.onboarding2,
    ),
    Page(
        title = "Achieve Your Goals",
        description = "Our tools help you set, track, and achieve your personal goals. Get started now and see progress like never before!",
        image = R.drawable.onboarding3,
    )
)