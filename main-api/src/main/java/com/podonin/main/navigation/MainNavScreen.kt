package com.podonin.main.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class MainNavScreen {

    @Serializable
    data object Quotes : MainNavScreen()
}