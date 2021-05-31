package com.tbse.wnsw.ui.aplist.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.tbse.wnsw.models.AccessPoint
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProvider

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2017
 */
@Preview
@Composable
fun AccessPointListItemIconRow(
    @PreviewParameter(
        provider = AccessPointPreviewProvider::class
    ) accessPoint: AccessPoint,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(top = 10.dp)
    ) {
        AccessPointListItemIconRowStrength(accessPoint.strength)
        AccessPointListItemIconRowSuggested(accessPoint.isSuggested)
        AccessPointListItemIconRowSecured(accessPoint.capabilities.isNotEmpty())
    }
}
