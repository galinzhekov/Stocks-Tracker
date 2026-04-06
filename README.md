# Stocks Tracker

Android interview task implementation built with Jetpack Compose, Room, Ktor, and Dagger Hilt.

The app loads Binance 24hr ticker data from [https://api2.binance.com/api/v3/ticker/24hr](https://api2.binance.com/api/v3/ticker/24hr), shows it in a list screen, supports pull-to-refresh, opens a details screen, and keeps the last fetched data available offline through Room.

## Features

- Compose-only UI
- Stocks list screen
- Stock details screen
- Pull-to-refresh for updating data
- Loading state
- Offline-first cached data after a successful fetch
- No hardcoded UI text in screens
- Feature-based, layered modular architecture

## Tech Stack

- Kotlin
- Jetpack Compose
- Room
- Dagger Hilt
- Ktor
- Kotlin Coroutines and Flow
- Navigation Compose
- KSP

## Architecture

The project is organized by layers and features.

### Core modules

- `:core:model` - domain models shared across modules
- `:core:network` - Ktor API layer and DTOs
- `:core:database` - Room entities, DAO, and database setup
- `:core:repository` - repository contracts
- `:core:data` - repository implementations and data mapping
- `:core:ui` - shared UI theme and reusable Compose components
- `:core:navigation` - shared navigation contracts and navigator abstraction

### Feature and app modules

- `:feature:stocks` - stocks presentation, MVI contracts, route bindings, and ViewModels
- `:navigation:stocks` - NavHost implementation for the stocks flow
- `:app` - application entry point and top-level wiring

## UI Pattern

The screens follow an MVI-style setup:

- screen composables accept only `state` and `intent`
- route composables connect ViewModels to screens
- navigation is dispatched from ViewModels through `AppNavigator`

Examples:

- `MyStocksRoute` -> `MyStocksScreen(state, intent)`
- `StockDetailsRoute` -> `StockDetailsScreen(state, intent)`

## Data Flow

1. The app fetches Binance ticker data through Ktor.
2. The response is mapped and stored in Room.
3. The UI observes Room data with Flow.
4. When offline, the app continues showing the last cached successful result.

## Screens

### My Stocks

Each item follows the requested format:

- line 1: `symbol (priceChangePercent%)`
- line 2: `bid/ask: bidPrice/askPrice`

The screen also supports pull-to-refresh.

### Stock Details

The details screen shows all ticker properties as repeated label/value blocks and supports scrolling for long content.

## Running the project

1. Open the project in Android Studio.
2. Sync Gradle.
3. Run the `app` configuration on an emulator or Android device.

## Notes

- The data source is Binance public ticker data.
- Room is used for persistence so previously fetched data is available offline.
- Shared UI elements are extracted into `:core:ui/components`.
- Navigation is separated into its own module instead of being embedded in the feature screens.
