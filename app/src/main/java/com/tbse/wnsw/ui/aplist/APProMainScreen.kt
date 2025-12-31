package com.tbse.wnsw.ui.aplist

import android.net.wifi.WifiNetworkSuggestion
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.tbse.wnsw.models.AccessPointUI
import com.tbse.wnsw.ui.aplist.preview.APListUiStatePreviewProvider
import com.tbse.wnsw.ui.theme.NewWNSWTheme

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2022
 */
@Composable
fun APProMainScreen(
    apViewModel: APListViewModel,
    setLastLoad: () -> Unit,
    addNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
    removeNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
) {

    val uiState = apViewModel.uiState.collectAsState().value

    APProMainScreenContent(
        uiState = uiState,
        setLastLoad = setLastLoad,
        addNetworkSuggestions = addNetworkSuggestions,
        removeNetworkSuggestions = removeNetworkSuggestions,
        onFavoriteToggle = { bssid, isFavorite -> apViewModel.updateFavorite(bssid, isFavorite) },
        onNetworkTap = { ap -> apViewModel.connectToNetwork(ap) }
    )
}

@Composable
private fun APProMainScreenContent(
    uiState: APListUiState,
    setLastLoad: () -> Unit,
    addNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit,
    removeNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit,
    onFavoriteToggle: (String, Boolean) -> Unit = { _, _ -> },
    onNetworkTap: (AccessPointUI) -> Unit = {},
) {
    Scaffold(
        topBar = {
            ApListAppBar()
        },
        content = { paddingValues ->
            when (uiState) {
                is APListUiState.HasListOfAPs -> {
                    APListLazyColumn(
                        uiState.aps,
                        paddingValues,
                        addNetworkSuggestions,
                        removeNetworkSuggestions,
                        onFavoriteToggle,
                        onNetworkTap
                    )
                }
                APListUiState.NoAPs -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextField(value = "No APs", onValueChange = {})
                    }
                }
            }

        },
        floatingActionButton = {
            APListFAB(
                setLastLoad = setLastLoad
            )
        }
    )
}

@Preview
@Composable
private fun APProMainScreenPreview(
    @PreviewParameter(APListUiStatePreviewProvider::class)
    uiState: APListUiState,
) {
    NewWNSWTheme {
        APProMainScreenContent(
            uiState = uiState,
            setLastLoad = {},
            addNetworkSuggestions = {},
            removeNetworkSuggestions = {}
        )
    }
}
