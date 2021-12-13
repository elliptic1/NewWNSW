package com.tbse.wnsw.ui.aplist

import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSuggestion
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tbse.wnsw.TAG
import com.tbse.wnsw.models.AccessPointUI
import com.tbse.wnsw.ui.aplist.item.AccessPointListItem

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2022
 */
@Composable
fun AccessPointList(
    modifier: Modifier = Modifier,
    itemViewStates: List<AccessPointUI>,
    setLastLoad: () -> Unit,
    addNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
    removeNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
) {
    Scaffold(
        topBar = {
            ApListAppBar()
        },
        content = {
            LazyColumn(modifier = modifier) {
                items(
                    items = itemViewStates,
                    key = { data -> data.BSSID }
                ) { data ->
                    AccessPointListItem(accessPoint = data,
                        addNetworkSuggestions,
                        removeNetworkSuggestions)
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

private fun List<AccessPointUI>.log() {
    Log.d(TAG, "Access Points:")
    this.forEachIndexed { index, ap ->
        Log.d(TAG, " - $index. ${ap.SSID} [${ap.BSSID}]")
        Log.d(TAG, " - - capabilities: ${ap.capabilities}")
        Log.d(TAG, " - - channel: ${ap.channel}")
    }
    Log.d(TAG, "---------------")
}