package com.tbse.wnsw.ui.aplist

import android.net.wifi.WifiNetworkSuggestion
import android.view.MotionEvent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tbse.wnsw.R
import com.tbse.wnsw.models.AccessPointUI
import com.tbse.wnsw.ui.aplist.item.AccessPointListItem
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProviderMany

/**
 * Created by toddsmith on 1/23/22.
 */
@Composable
fun APListLazyColumn(
    aps: List<AccessPointUI>,
    paddingValues: PaddingValues,
    addNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
    removeNetworkSuggestions: (List<WifiNetworkSuggestion>) -> Unit = {},
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(
            items = aps,
            key = { data -> data.BSSID }
        ) { data ->
            val isClicked = remember { mutableStateOf(false) }
            fun onItemTouch(motionEvent: MotionEvent): Boolean {
                val ret = onAPItemTouch(motionEvent)
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
}

private fun onAPItemTouch(motionEvent: MotionEvent): Boolean {
    return when (motionEvent.action) {
        MotionEvent.ACTION_DOWN -> true
        MotionEvent.ACTION_MOVE -> true
        else -> false
    }
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

@Composable
@Preview
fun APListLazyColumnPreview() {
    APListLazyColumn(
        AccessPointPreviewProviderMany().values.toList(),
        PaddingValues(0.dp),
        {},
        {}
    )
}
