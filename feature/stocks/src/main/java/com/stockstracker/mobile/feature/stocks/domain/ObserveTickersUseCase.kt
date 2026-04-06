package com.stockstracker.mobile.feature.stocks.domain

import com.stockstracker.mobile.core.model.MarketTicker
import com.stockstracker.mobile.core.repository.StocksRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveTickersUseCase @Inject constructor(
    private val stocksRepository: StocksRepository
) {
    operator fun invoke(): Flow<List<MarketTicker>> = stocksRepository.observeTickers()
}
