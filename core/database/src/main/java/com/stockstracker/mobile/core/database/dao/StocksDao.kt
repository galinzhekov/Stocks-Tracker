package com.stockstracker.mobile.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stockstracker.mobile.core.database.entity.StockQuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StocksDao {
    @Query("SELECT * FROM market_tickers ORDER BY symbol ASC")
    fun observeTickers(): Flow<List<StockQuoteEntity>>

    @Query("SELECT * FROM market_tickers WHERE symbol = :symbol LIMIT 1")
    fun observeTicker(symbol: String): Flow<StockQuoteEntity?>

    @Query("DELETE FROM market_tickers")
    suspend fun clearTickers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTickers(quotes: List<StockQuoteEntity>)
}
