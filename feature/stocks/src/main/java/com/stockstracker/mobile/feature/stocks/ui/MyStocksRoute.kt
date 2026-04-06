package com.stockstracker.mobile.feature.stocks.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MyStocksRoute(
    viewModel: MyStocksViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyStocksScreen(
        state = uiState,
        intent = viewModel::onIntent
    )
}
