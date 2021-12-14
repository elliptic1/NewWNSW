package com.tbse.wnsw.ui.aplist

import android.net.wifi.WifiNetworkSuggestion
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.tbse.wnsw.R
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
                    val isClicked = remember { mutableStateOf(false) }
                    fun onItemTouch(motionEvent: MotionEvent): Boolean {
                        val ret = when (motionEvent.action) {
                            MotionEvent.ACTION_DOWN -> true
                            MotionEvent.ACTION_MOVE -> true
                            else -> false
                        }
                        isClicked.value = ret
                        return ret
                    }
                    AccessPointListItem(accessPoint = data,
                        addNetworkSuggestions,
                        removeNetworkSuggestions,
                        ::onItemTouch,
                        getBGColor(isClicked = isClicked.value)
                    )
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

@Composable
private fun getBGColor(isClicked: Boolean): Color {
    return colorResource(
        if (isClicked.not()) {
            R.color.grey
        } else {
            R.color.green
        }
    )
}

private fun onItemTouch(motionEvent: MotionEvent): Boolean {
    return when (motionEvent.action) {
        MotionEvent.ACTION_DOWN -> true
        MotionEvent.ACTION_MOVE -> true
        else -> false
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