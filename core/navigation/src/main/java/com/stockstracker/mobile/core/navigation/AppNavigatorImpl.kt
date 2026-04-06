package com.stockstracker.mobile.core.navigation

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Singleton
class AppNavigatorImpl @Inject constructor() : AppNavigator {
    private val _commands = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)

    override val commands = _commands.asSharedFlow()

    override suspend fun navigate(command: NavigationCommand) {
        _commands.emit(command)
    }
}
