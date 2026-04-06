package com.stockstracker.mobile.core.network.service

import com.stockstracker.mobile.core.network.model.BinanceTickerDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BinanceTickerApiService(
    private val httpClient: HttpClient
) {
    suspend fun fetchTickers(): List<BinanceTickerDto> =
        httpClient.get("https://api2.binance.com/api/v3/ticker/24hr").body()
}
