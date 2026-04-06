package com.stockstracker.mobile.core.navigation

import kotlinx.coroutines.flow.SharedFlow

interface AppNavigator {
    val commands: SharedFlow<NavigationCommand>

    suspend fun navigate(command: NavigationCommand)
}
