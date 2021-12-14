package com.tbse.wnsw.ui.aplist.item

import android.net.wifi.WifiNetworkSuggestion
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.tbse.wnsw.R
import com.tbse.wnsw.models.AccessPointUI
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProvider

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2022
 */
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun AccessPointListItem(
    @PreviewParameter(
        provider = AccessPointPreviewProvider::class
    ) accessPoint: AccessPointUI,
    addNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
    removeNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
    onTouchEvent: (MotionEvent) -> Boolean = { false },
    bg: Color = colorResource(id = R.color.grey),
) {
    Row(
        Modifier
            .background(bg)
            .fillMaxWidth()
            .height(90.dp)
            .pointerInteropFilter {
                onTouchEvent(it)
            }
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
        ) {
            AccessPointListItemSSID(accessPoint.SSID)
            AccessPointListItemBSSID(
                accessPoint.BSSID,
                accessPoint.channel
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            AccessPointListItemSuggestSwitch(
                accessPoint,
                addNetworkSuggestions,
                removeNetworkSuggestions
            )
            AccessPointListItemIconRow(accessPoint)
        }
    }
}
