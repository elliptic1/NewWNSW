package com.tbse.wnsw.ui.aplist

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tbse.wnsw.TAG
import com.tbse.wnsw.models.AccessPoint
import com.tbse.wnsw.ui.aplist.item.AccessPointListItem

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2017
 */
@Composable
fun AccessPointList(
    modifier: Modifier = Modifier,
    itemViewStates: List<AccessPoint>,
) {
    LazyColumn(modifier = modifier) {
        itemViewStates.log()
        items(itemViewStates) { data ->
            AccessPointListItem(accessPoint = data)
        }
    }
}

private fun List<AccessPoint>.log() {
    Log.d(TAG, "Access Points:")
    this.forEachIndexed { index, ap ->
        Log.d(TAG, " - $index. ${ap.SSID} [${ap.BSSID}]")
        Log.d(TAG, " - - capabilities: ${ap.capabilities}")
        Log.d(TAG, " - - channel: ${ap.channel}")
    }
    Log.d(TAG, "---------------")
}