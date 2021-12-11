package com.tbse.wnsw.ui.aplist

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tbse.wnsw.TAG
import com.tbse.wnsw.models.AccessPointUI
import com.tbse.wnsw.ui.aplist.item.AccessPointListItem
import java.time.LocalTime

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2022
 */
@Composable
fun AccessPointList(
    modifier: Modifier = Modifier,
    itemViewStates: List<AccessPointUI>,
    lastLoad: LocalTime
) {
    LazyColumn(modifier = modifier) {
        items(
            items = itemViewStates,
            key = { data -> data.BSSID }
        ) { data ->
            AccessPointListItem(accessPoint = data)
        }
    }
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