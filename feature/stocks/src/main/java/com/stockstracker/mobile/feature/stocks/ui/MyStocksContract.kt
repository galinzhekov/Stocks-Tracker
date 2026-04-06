package com.stockstracker.mobile.feature.stocks.ui

sealed interface MyStocksIntent {
    data object Refresh : MyStocksIntent
    data object DismissError : MyStocksIntent
    data class OpenStockDetails(val symbol: String) : MyStocksIntent
}
