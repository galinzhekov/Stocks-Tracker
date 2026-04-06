package com.stockstracker.mobile.core.data.di

import android.content.Context
import androidx.room.Room
import com.stockstracker.mobile.core.data.repository.DefaultStocksRepository
import com.stockstracker.mobile.core.database.StocksTrackerDatabase
import com.stockstracker.mobile.core.database.dao.StocksDao
import com.stockstracker.mobile.core.network.service.BinanceTickerApiService
import com.stockstracker.mobile.core.repository.StocksRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindStocksRepository(
        repository: DefaultStocksRepository
    ): StocksRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun provideHttpClient(json: Json): HttpClient =
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(json)
            }
        }

    @Provides
    @Singleton
    fun provideBinanceTickerApiService(httpClient: HttpClient): BinanceTickerApiService =
        BinanceTickerApiService(httpClient)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): StocksTrackerDatabase =
        Room.databaseBuilder(
            context,
            StocksTrackerDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideStocksDao(database: StocksTrackerDatabase): StocksDao =
        database.stocksDao()

    private const val DATABASE_NAME = "stocks-tracker.db"
}
