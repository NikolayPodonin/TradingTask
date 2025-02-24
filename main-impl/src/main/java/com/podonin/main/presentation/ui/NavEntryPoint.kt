package com.podonin.main.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.podonin.main.navigation.MainNavScreen
import com.podonin.main.presentation.MainViewModel
import com.podonin.quotes_api.navigation.QuotesEntryPoint

@Composable
fun NavEntryPoint(
    quotesEntryPoint: QuotesEntryPoint,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val screen by mainViewModel.screenFlow.collectAsState(
        MainNavScreen.Quotes
    )

    val navController = rememberNavController()
    NavHost(navController, startDestination = screen) {
        composable<MainNavScreen.Quotes> {
            quotesEntryPoint.EntryPoint()
        }
    }
}