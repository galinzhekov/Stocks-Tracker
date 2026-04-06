package com.stockstracker.mobile.feature.stocks.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stockstracker.mobile.core.navigation.AppNavigator
import com.stockstracker.mobile.core.navigation.NavigationCommand
import com.stockstracker.mobile.core.model.MarketTicker
import com.stockstracker.mobile.feature.stocks.R
import com.stockstracker.mobile.feature.stocks.domain.ObserveTickerDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class StockDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeTickerDetailsUseCase: ObserveTickerDetailsUseCase,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val symbol: String = savedStateHandle.get<String>("symbol").orEmpty()

    val uiState = observeTickerDetailsUseCase(symbol)
        .map { stock ->
            stock?.toUiState() ?: StockDetailsUiState(
                symbol = symbol,
                isMissing = true
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StockDetailsUiState(symbol = symbol, isMissing = true)
        )

    fun onIntent(intent: StockDetailsIntent) {
        when (intent) {
            StockDetailsIntent.GoBack -> {
                viewModelScope.launch {
                    appNavigator.navigate(NavigationCommand.GoBack)
                }
            }
        }
    }
}

data class StockDetailsUiState(
    val symbol: String,
    val fields: List<DetailFieldUiModel> = emptyList(),
    val isMissing: Boolean = false
)

data class DetailFieldUiModel(
    val labelRes: Int,
    val value: String
)

sealed interface StockDetailsIntent {
    data object GoBack : StockDetailsIntent
}

private fun MarketTicker.toUiState(): StockDetailsUiState =
    StockDetailsUiState(
        symbol = symbol,
        fields = listOf(
            DetailFieldUiModel(R.string.symbol_label, symbol),
            DetailFieldUiModel(R.string.price_change_label, priceChange),
            DetailFieldUiModel(R.string.price_change_percent_label, priceChangePercent),
            DetailFieldUiModel(R.string.weighted_avg_price_label, weightedAvgPrice),
            DetailFieldUiModel(R.string.prev_close_price_label, prevClosePrice),
            DetailFieldUiModel(R.string.last_price_label, lastPrice),
            DetailFieldUiModel(R.string.last_qty_label, lastQty),
            DetailFieldUiModel(R.string.bid_price_label, bidPrice),
            DetailFieldUiModel(R.string.bid_qty_label, bidQty),
            DetailFieldUiModel(R.string.ask_price_label, askPrice),
            DetailFieldUiModel(R.string.ask_qty_label, askQty),
            DetailFieldUiModel(R.string.open_price_label, openPrice),
            DetailFieldUiModel(R.string.high_price_label, highPrice),
            DetailFieldUiModel(R.string.low_price_label, lowPrice),
            DetailFieldUiModel(R.string.volume_label, volume),
            DetailFieldUiModel(R.string.quote_volume_label, quoteVolume),
            DetailFieldUiModel(R.string.open_time_label, openTime.toString()),
            DetailFieldUiModel(R.string.close_time_label, closeTime.toString()),
            DetailFieldUiModel(R.string.first_id_label, firstId.toString()),
            DetailFieldUiModel(R.string.last_id_label, lastId.toString()),
            DetailFieldUiModel(R.string.count_label, count.toString()),
            DetailFieldUiModel(R.string.cached_at_label, formatDetailTimestamp(lastUpdatedAt))
        ),
        isMissing = false
    )

private fun formatDetailTimestamp(timestamp: Long): String = timestamp.toString()
