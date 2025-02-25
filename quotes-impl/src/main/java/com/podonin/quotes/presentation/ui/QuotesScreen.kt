package com.podonin.quotes.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.podonin.quotes.presentation.QuotesViewModel
import com.podonin.quotes_impl.R
import com.podonin.common_ui.R as CommonUiR

@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    viewModel: QuotesViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val emptyInsets = WindowInsets(0, 0, 0, 0)
    Scaffold(
        snackbarHost = { ErrorSnackbarHost(snackbarHostState) },
        contentWindowInsets = emptyInsets
    ) { paddingValues ->
        val errorMessage = stringResource(R.string.quotes_error_message)
        LaunchedEffect(Unit) {
            viewModel.errorFlow.collect {
                snackbarHostState.showSnackbar(message = errorMessage)
            }
        }

        val quotes by viewModel.quotesFlow.collectAsState()
        Box(modifier = modifier.padding(paddingValues)) {
            if (quotes.isEmpty()) {
                EmptyStateScreen()
            } else {
                ListStateScreen(quotes)
            }
        }
    }
}

@Composable
fun ErrorSnackbarHost(snackbarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackbarHostState) { data ->
        Snackbar(
            snackbarData = data,
            modifier = Modifier
                .padding(top = dimensionResource(CommonUiR.dimen.material_margin_big))
                .fillMaxWidth(),
            containerColor = colorResource(R.color.red),
            contentColor = colorResource(R.color.white),
            shape = RoundedCornerShape(dimensionResource(CommonUiR.dimen.material_margin_medium))
        )
    }
}
