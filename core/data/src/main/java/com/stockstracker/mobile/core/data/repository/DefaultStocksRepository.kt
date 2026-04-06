package com.stockstracker.mobile.core.data.repository

import com.stockstracker.mobile.core.data.mapper.toDomain
import com.stockstracker.mobile.core.data.mapper.toEntity
import com.stockstracker.mobile.core.database.dao.StocksDao
import com.stockstracker.mobile.core.model.MarketTicker
import com.stockstracker.mobile.core.network.service.BinanceTickerApiService
import com.stockstracker.mobile.core.repository.StocksRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DefaultStocksRepository @Inject constructor(
    private val stocksDao: StocksDao,
    private val binanceTickerApiService: BinanceTickerApiService
) : StocksRepository {

    override fun observeTickers(): Flow<List<MarketTicker>> =
        stocksDao.observeTickers().map { stocks ->
            stocks.map { it.toDomain() }
        }

    override fun observeTicker(symbol: String): Flow<MarketTicker?> =
        stocksDao.observeTicker(symbol).map { ticker ->
            ticker?.toDomain()
        }

    override suspend fun refreshTickers() {
        val updatedAt = System.currentTimeMillis()
        val tickers = binanceTickerApiService.fetchTickers()
        stocksDao.clearTickers()
        stocksDao.upsertTickers(
            tickers.map { ticker ->
                ticker.toEntity(updatedAt = updatedAt)
            }
        )
    }
}
