package com.tbse.wnsw.ui.aplist

import android.net.wifi.WifiNetworkSuggestion
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
    onFavoriteToggle: (String, Boolean) -> Unit = { _, _ -> },
    onNetworkTap: (AccessPointUI) -> Unit = {},
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(
            items = aps,
            key = { data -> data.BSSID }
        ) { data ->
            AccessPointListItem(
                accessPoint = data,
                addNetworkSuggestions = addNetworkSuggestions,
                removeNetworkSuggestions = removeNetworkSuggestions,
                onFavoriteToggle = onFavoriteToggle,
                onNetworkClick = { onNetworkTap(data) },
            )
        }
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

@Preview
@Composable
private fun GetBGColorPreview() {
    Row {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(getBGColor(isClicked = false))
        )
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(getBGColor(isClicked = true))
        )
    }
}
