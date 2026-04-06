package com.stockstracker.mobile.core.data.mapper

import com.stockstracker.mobile.core.database.entity.StockQuoteEntity
import com.stockstracker.mobile.core.model.MarketTicker
import com.stockstracker.mobile.core.network.model.BinanceTickerDto

internal fun StockQuoteEntity.toDomain(): MarketTicker =
    MarketTicker(
        symbol = symbol,
        priceChange = priceChange,
        priceChangePercent = priceChangePercent,
        weightedAvgPrice = weightedAvgPrice,
        prevClosePrice = prevClosePrice,
        lastPrice = lastPrice,
        lastQty = lastQty,
        bidPrice = bidPrice,
        bidQty = bidQty,
        askPrice = askPrice,
        askQty = askQty,
        openPrice = openPrice,
        highPrice = highPrice,
        lowPrice = lowPrice,
        volume = volume,
        quoteVolume = quoteVolume,
        openTime = openTime,
        closeTime = closeTime,
        firstId = firstId,
        lastId = lastId,
        count = count,
        lastUpdatedAt = lastUpdatedAt
    )

internal fun BinanceTickerDto.toEntity(updatedAt: Long): StockQuoteEntity =
    StockQuoteEntity(
        symbol = symbol,
        priceChange = priceChange,
        priceChangePercent = priceChangePercent,
        weightedAvgPrice = weightedAvgPrice,
        prevClosePrice = prevClosePrice,
        lastPrice = lastPrice,
        lastQty = lastQty,
        bidPrice = bidPrice,
        bidQty = bidQty,
        askPrice = askPrice,
        askQty = askQty,
        openPrice = openPrice,
        highPrice = highPrice,
        lowPrice = lowPrice,
        volume = volume,
        quoteVolume = quoteVolume,
        openTime = openTime,
        closeTime = closeTime,
        firstId = firstId,
        lastId = lastId,
        count = count,
        lastUpdatedAt = updatedAt
    )
