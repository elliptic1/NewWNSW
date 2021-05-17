package com.tbse.wnsw.ui.aplist.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.tbse.wnsw.R
import com.tbse.wnsw.models.AccessPoint
import com.tbse.wnsw.ui.aplist.AccessPointListItemIconRow
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProvider

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2017
 */
@Preview
@Composable
fun AccessPointListItem(
    @PreviewParameter(
        provider = AccessPointPreviewProvider::class
    ) accessPoint: AccessPoint
) {
    Row(
        Modifier
            .background(colorResource(R.color.darkgrey))
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
        ) {
            AccessPointListItemSSID(accessPoint)
            AccessPointListItemBSSID(accessPoint)
        }
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            AccessPointListItemAutoSwitch(accessPoint)
            AccessPointListItemIconRow(accessPoint)
        }
    }
}
