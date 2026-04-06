package com.stockstracker.mobile.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_tickers")
data class StockQuoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "price_change")
    val priceChange: String,
    @ColumnInfo(name = "price_change_percent")
    val priceChangePercent: String,
    @ColumnInfo(name = "weighted_avg_price")
    val weightedAvgPrice: String,
    @ColumnInfo(name = "prev_close_price")
    val prevClosePrice: String,
    @ColumnInfo(name = "last_price")
    val lastPrice: String,
    @ColumnInfo(name = "last_qty")
    val lastQty: String,
    @ColumnInfo(name = "bid_price")
    val bidPrice: String,
    @ColumnInfo(name = "bid_qty")
    val bidQty: String,
    @ColumnInfo(name = "ask_price")
    val askPrice: String,
    @ColumnInfo(name = "ask_qty")
    val askQty: String,
    @ColumnInfo(name = "open_price")
    val openPrice: String,
    @ColumnInfo(name = "high_price")
    val highPrice: String,
    @ColumnInfo(name = "low_price")
    val lowPrice: String,
    @ColumnInfo(name = "volume")
    val volume: String,
    @ColumnInfo(name = "quote_volume")
    val quoteVolume: String,
    @ColumnInfo(name = "open_time")
    val openTime: Long,
    @ColumnInfo(name = "close_time")
    val closeTime: Long,
    @ColumnInfo(name = "first_id")
    val firstId: Long,
    @ColumnInfo(name = "last_id")
    val lastId: Long,
    @ColumnInfo(name = "count")
    val count: Long,
    @ColumnInfo(name = "last_updated_at")
    val lastUpdatedAt: Long
)
