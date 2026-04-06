package com.stockstracker.mobile.feature.stocks.ui

data class MyStocksUiState(
    val isRefreshing: Boolean = false,
    val stocks: List<StockCardUiModel> = emptyList(),
    val lastUpdatedAt: Long? = null,
    val errorMessageRes: Int? = null
) {
    val isEmpty: Boolean
        get() = stocks.isEmpty()
}

data class StockCardUiModel(
    val symbol: String,
    val priceChangePercent: String,
    val bidPrice: String,
    val askPrice: String,
    val lastUpdatedAt: Long
)
