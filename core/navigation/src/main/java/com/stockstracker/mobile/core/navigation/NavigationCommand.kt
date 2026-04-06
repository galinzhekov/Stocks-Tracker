package com.stockstracker.mobile.core.navigation

sealed interface NavigationCommand {
    data class ToStockDetails(val symbol: String) : NavigationCommand
    data object GoBack : NavigationCommand
}
