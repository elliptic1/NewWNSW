package com.tbse.wnsw.ui.aplist

import android.net.wifi.WifiNetworkSuggestion
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.tbse.wnsw.TAG
import com.tbse.wnsw.models.AccessPointUI

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
                        removeNetworkSuggestions
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
