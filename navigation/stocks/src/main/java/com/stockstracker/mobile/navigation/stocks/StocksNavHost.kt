package com.stockstracker.mobile.navigation.stocks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.stockstracker.mobile.core.navigation.AppNavigator
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stockstracker.mobile.core.navigation.NavigationCommand
import com.stockstracker.mobile.core.navigation.Screen
import com.stockstracker.mobile.feature.stocks.ui.MyStocksRoute
import com.stockstracker.mobile.feature.stocks.ui.StockDetailsRoute
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StocksNavHost(
    appNavigator: AppNavigator,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    LaunchedEffect(appNavigator, navController) {
        appNavigator.commands.collectLatest { command ->
            when (command) {
                NavigationCommand.GoBack -> navController.popBackStack()
                is NavigationCommand.ToStockDetails -> {
                    navController.navigate(Screen.StockDetails.createRoute(command.symbol))
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.MyStocks.route,
        modifier = modifier
    ) {
        composable(route = Screen.MyStocks.route) {
            MyStocksRoute()
        }
        composable(
            route = Screen.StockDetails.route,
            arguments = listOf(
                navArgument("symbol") {
                    type = NavType.StringType
                }
            )
        ) {
            StockDetailsRoute()
        }
    }
}
