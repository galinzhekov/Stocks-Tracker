package com.stockstracker.mobile.core.repository

import com.stockstracker.mobile.core.model.MarketTicker
import kotlinx.coroutines.flow.Flow

interface StocksRepository {
    fun observeTickers(): Flow<List<MarketTicker>>

    fun observeTicker(symbol: String): Flow<MarketTicker?>

    suspend fun refreshTickers()
}
