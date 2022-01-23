/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2022
 */
package com.tbse.wnsw.ui.aplist.item

import android.net.MacAddress
import android.net.wifi.WifiNetworkSuggestion
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tbse.wnsw.models.AccessPointUI
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProvider

@Composable
fun AccessPointListItemSuggestSwitch(
    accessPoint: AccessPointUI,
    addNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
    removeNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                paddingValues = PaddingValues(
                    start = 5.dp, top = 5.dp, end = 5.dp, bottom = 0.dp
                )
            )
    ) {
        val switchState = remember {
            mutableStateOf(accessPoint.isSuggested)
        }

        Switch(
            enabled = true,
            checked = switchState.value,
            colors = SwitchDefaults.colors(),
            onCheckedChange = { newValue ->
                switchState.value = newValue
                if (newValue) {
                    addNetworkSuggestions(
                        getWifiNetworkSuggestionList(accessPoint)
                    )
                } else {
                    removeNetworkSuggestions(
                        getWifiNetworkSuggestionList(accessPoint)
                    )
                }
            },
            modifier = Modifier
                .width(40.dp)
                .align(Alignment.Center)
        )
    }
}

private fun getWifiNetworkSuggestionList(ap: AccessPointUI): List<WifiNetworkSuggestion> {
    return listOf(
        WifiNetworkSuggestion.Builder()
            .setBssid(MacAddress.fromString(ap.BSSID))
            .setSsid(ap.SSID)
            .build()
    )
}

@Preview
@Composable
fun AccessPointListItemSuggestSwitchPreview(
    accessPoint: AccessPointUI = AccessPointPreviewProvider().values.first(),
    addNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
    removeNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
) {
    AccessPointListItemSuggestSwitch(
        accessPoint, {}, {}
    )
}
