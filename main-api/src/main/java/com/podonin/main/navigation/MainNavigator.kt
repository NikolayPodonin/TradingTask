package com.podonin.main.navigation

import kotlinx.coroutines.flow.SharedFlow

interface MainNavigator {
    val screenFlow: SharedFlow<MainNavScreen>
    fun navigateToQuotes()
}