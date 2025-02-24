package com.podonin.quotes.navigation

import androidx.compose.runtime.Composable
import com.podonin.quotes.presentation.QuotesScreen
import com.podonin.quotes_api.navigation.QuotesEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotesEntryPointImpl @Inject constructor() : QuotesEntryPoint {
    @Composable
    override fun EntryPoint() {
        QuotesScreen()
    }
}