# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

WNSW (Wi-Fi Network Scanner for Watching) is an Android app that scans for nearby Wi-Fi access points and allows users to mark networks as "suggested" for the system's Wi-Fi network suggestion API.

## Build Commands

```bash
# Build all modules
./gradlew build

# Build debug APK
./gradlew :app:assembleDebug

# Run unit tests
./gradlew test

# Run a single unit test class
./gradlew :app:testDebugUnitTest --tests "com.tbse.wnsw.ExampleUnitTest"

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Clean build
./gradlew clean build
```

## Architecture

The project follows Clean Architecture with a multi-module structure:

```
app/                  → UI layer (Jetpack Compose, ViewModel, Hilt injection)
wifiSystem/           → System access layer (WifiManager, BroadcastReceivers)
wifiDomain/           → Domain layer (Repository interfaces/impl, Domain models)
wifiDatabase/         → Data layer (Room database, DAOs, Entity models)
wifiSupport/          → Shared utilities (ModelMapper interface)
buildSrc/             → Gradle dependency management (version catalogs as Kotlin objects)
```

### Data Flow

1. **Android System** → `ScanResultMapper` converts `ScanResult` to `AccessPoint` entity
2. **wifiDatabase** → Room persists `AccessPoint` entities via `APDao`
3. **wifiDomain** → `APRepositoryImpl` maps entities to `AccessPointDomain` models
4. **app** → `AccessPointMapper` converts domain models to `AccessPointUI` for Compose

### Key Patterns

- **ModelMapper<I, O>** (`wifiSupport`): Functional interface for type conversions between layers
- **Hilt DI**: Each module has its own DI module (e.g., `CommonModule`, `DatabaseModule`, `MapperModule`)
- **Flows**: Repository returns `Flow<List<AccessPointDomain>>` for reactive updates
- **UiState**: Sealed interface pattern in ViewModels (`APListUiState.NoAPs`, `APListUiState.HasListOfAPs`)

### Module Dependencies

```
app → wifiDomain → wifiDatabase
    → wifiSystem → wifiDatabase
    → wifiSupport (shared by all)
```

## Key Files

- `MainActivity.kt` - Entry point; handles permissions, Wi-Fi scan registration, network suggestions
- `APListViewModel.kt` - Exposes `APListUiState` flow to Compose UI
- `APRepository.kt` / `APRepositoryImpl.kt` - Domain layer data access
- `APDao.kt` / `APDatabase.kt` - Room database operations
- `ScanResultMapper.kt` - Converts Android `ScanResult` to database entity

## Required Permissions

The app requires these runtime permissions (handled in `MainActivity`):
- `ACCESS_FINE_LOCATION` - Required for Wi-Fi scanning
- `ACCESS_WIFI_STATE` - Read Wi-Fi state
- `CHANGE_WIFI_STATE` - Add/remove network suggestions

## CI/CD

GitHub Actions workflow runs `./gradlew build` on pushes/PRs to `main` branch using JDK 11.
