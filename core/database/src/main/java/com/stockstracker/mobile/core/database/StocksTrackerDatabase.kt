package com.stockstracker.mobile.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stockstracker.mobile.core.database.dao.StocksDao
import com.stockstracker.mobile.core.database.entity.StockQuoteEntity

@Database(
    entities = [StockQuoteEntity::class],
    version = 2,
    exportSchema = true
)
abstract class StocksTrackerDatabase : RoomDatabase() {
    abstract fun stocksDao(): StocksDao
}
