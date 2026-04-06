package com.stockstracker.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.stockstracker.mobile.core.navigation.AppNavigator
import com.stockstracker.mobile.core.ui.theme.StocksTrackerTheme
import com.stockstracker.mobile.navigation.stocks.StocksNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StocksTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    StocksNavHost(appNavigator = appNavigator)
                }
            }
        }
    }
}
