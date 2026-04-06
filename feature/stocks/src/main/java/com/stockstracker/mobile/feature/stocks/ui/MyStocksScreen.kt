package com.stockstracker.mobile.feature.stocks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stockstracker.mobile.core.ui.components.CenteredLoadingIndicator
import com.stockstracker.mobile.core.ui.components.EmptyStateMessage
import com.stockstracker.mobile.core.ui.components.ErrorMessageCard
import com.stockstracker.mobile.feature.stocks.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyStocksScreen(
    state: MyStocksUiState,
    intent: (MyStocksIntent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.my_stocks)) }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            isRefreshing = state.isRefreshing,
            onRefresh = { intent(MyStocksIntent.Refresh) }
        ) {
            when {
                state.isRefreshing && state.isEmpty -> {
                    CenteredLoadingIndicator(
                        label = stringResource(R.string.loading_stocks),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                state.isEmpty -> {
                    EmptyStateMessage(
                        title = stringResource(R.string.no_stocks_available),
                        description = stringResource(R.string.pull_latest_market_data),
                        hint = stringResource(R.string.pull_to_refresh_hint),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    PortfolioContent(
                        uiState = state,
                        modifier = Modifier.fillMaxSize(),
                        intent = intent
                    )
                }
            }
        }
    }
}

@Composable
private fun PortfolioContent(
    uiState: MyStocksUiState,
    modifier: Modifier = Modifier,
    intent: (MyStocksIntent) -> Unit
) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            StocksListHeader(lastUpdatedAt = uiState.lastUpdatedAt)
        }
        if (uiState.errorMessageRes != null) {
            item {
                ErrorMessageCard(
                    message = stringResource(uiState.errorMessageRes),
                    dismissLabel = stringResource(R.string.dismiss),
                    onDismiss = { intent(MyStocksIntent.DismissError) }
                )
            }
        }
        items(uiState.stocks, key = { it.symbol }) { stock ->
            StockCard(
                stock = stock,
                onClick = { intent(MyStocksIntent.OpenStockDetails(stock.symbol)) }
            )
        }
    }
}

@Composable
private fun StocksListHeader(
    lastUpdatedAt: Long?
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = stringResource(R.string.market_snapshot),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = lastUpdatedAt?.let {
                stringResource(R.string.last_updated_value, formatTimestamp(it))
            } ?: stringResource(R.string.pull_to_refresh_hint),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun StockCard(
    stock: StockCardUiModel,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.stock_list_title,
                    stock.symbol,
                    stock.priceChangePercent
                ),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider()
            Text(
                text = stringResource(
                    R.string.bid_ask_value,
                    stock.bidPrice,
                    stock.askPrice
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formatter.format(Date(timestamp))
}
