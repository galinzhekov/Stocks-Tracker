package com.stockstracker.mobile.core.navigation

sealed class Screen(val route: String) {
    data object MyStocks : Screen("stocks/list")
    data object StockDetails : Screen("stocks/details/{symbol}") {
        fun createRoute(symbol: String): String = "stocks/details/$symbol"
    }
}
