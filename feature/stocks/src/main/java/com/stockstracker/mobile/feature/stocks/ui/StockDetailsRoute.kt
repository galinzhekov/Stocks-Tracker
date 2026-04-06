package com.stockstracker.mobile.feature.stocks.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StockDetailsRoute(
    viewModel: StockDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    StockDetailsScreen(
        state = uiState,
        intent = viewModel::onIntent
    )
}
