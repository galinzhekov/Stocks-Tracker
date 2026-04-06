package com.stockstracker.mobile.feature.stocks.domain

import com.stockstracker.mobile.core.repository.StocksRepository
import javax.inject.Inject

class RefreshTickersUseCase @Inject constructor(
    private val stocksRepository: StocksRepository
) {
    suspend operator fun invoke() {
        stocksRepository.refreshTickers()
    }
}
