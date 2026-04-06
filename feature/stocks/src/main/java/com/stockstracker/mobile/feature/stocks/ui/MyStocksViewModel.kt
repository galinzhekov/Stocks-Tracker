package com.stockstracker.mobile.feature.stocks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stockstracker.mobile.core.navigation.AppNavigator
import com.stockstracker.mobile.core.navigation.NavigationCommand
import com.stockstracker.mobile.core.model.MarketTicker
import com.stockstracker.mobile.feature.stocks.R
import com.stockstracker.mobile.feature.stocks.domain.ObserveTickersUseCase
import com.stockstracker.mobile.feature.stocks.domain.RefreshTickersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MyStocksViewModel @Inject constructor(
    observeTickersUseCase: ObserveTickersUseCase,
    private val refreshTickersUseCase: RefreshTickersUseCase,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val isRefreshing = MutableStateFlow(false)
    private val errorMessageRes = MutableStateFlow<Int?>(null)

    val uiState = combine(
        observeTickersUseCase(),
        isRefreshing,
        errorMessageRes
    ) { stocks, refreshing, errorRes ->
        stocks.toUiState(
            isRefreshing = refreshing,
            errorMessageRes = errorRes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = MyStocksUiState(isRefreshing = true)
    )

    init {
        refresh()
    }

    fun onIntent(intent: MyStocksIntent) {
        when (intent) {
            MyStocksIntent.DismissError -> dismissError()
            MyStocksIntent.Refresh -> refresh()
            is MyStocksIntent.OpenStockDetails -> openStockDetails(intent.symbol)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            errorMessageRes.value = null

            runCatching { refreshTickersUseCase() }
                .onFailure {
                    errorMessageRes.value = R.string.unable_to_refresh_stocks
                }

            isRefreshing.value = false
        }
    }

    fun dismissError() {
        errorMessageRes.value = null
    }

    private fun openStockDetails(symbol: String) {
        viewModelScope.launch {
            appNavigator.navigate(NavigationCommand.ToStockDetails(symbol))
        }
    }
}

private fun List<MarketTicker>.toUiState(
    isRefreshing: Boolean,
    errorMessageRes: Int?
): MyStocksUiState {
    val cards = map { stock ->
        StockCardUiModel(
            symbol = stock.symbol,
            priceChangePercent = stock.priceChangePercent,
            bidPrice = stock.bidPrice,
            askPrice = stock.askPrice,
            lastUpdatedAt = stock.lastUpdatedAt
        )
    }

    return MyStocksUiState(
        isRefreshing = isRefreshing,
        stocks = cards,
        lastUpdatedAt = cards.mapNotNull { it.lastUpdatedAt }.maxOrNull(),
        errorMessageRes = errorMessageRes
    )
}
