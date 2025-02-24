package com.podonin.main.navigation

import com.podonin.common_utils.mutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNavigatorImpl @Inject constructor(): MainNavigator {

    private val _screenFlow = mutableSharedFlow<MainNavScreen>()
    override val screenFlow = _screenFlow.asSharedFlow()

    override fun navigateToQuotes() {
        _screenFlow.tryEmit(MainNavScreen.Quotes)
    }
}