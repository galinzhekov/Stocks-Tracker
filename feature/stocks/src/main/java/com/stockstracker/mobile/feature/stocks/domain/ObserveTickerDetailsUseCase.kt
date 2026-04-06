package com.stockstracker.mobile.feature.stocks.domain

import com.stockstracker.mobile.core.model.MarketTicker
import com.stockstracker.mobile.core.repository.StocksRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveTickerDetailsUseCase @Inject constructor(
    private val stocksRepository: StocksRepository
) {
    operator fun invoke(symbol: String): Flow<MarketTicker?> = stocksRepository.observeTicker(symbol)
}
