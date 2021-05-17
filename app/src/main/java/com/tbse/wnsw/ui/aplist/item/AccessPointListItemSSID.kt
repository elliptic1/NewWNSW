package com.tbse.wnsw.ui.aplist.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.tbse.wnsw.R
import com.tbse.wnsw.models.AccessPoint
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProvider

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2017
 */
@Preview
@Composable
fun AccessPointListItemSSID(
    @PreviewParameter(
        provider = AccessPointPreviewProvider::class
    ) accessPoint: AccessPoint
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.60f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = accessPoint.SSID,
            fontSize = 26.sp,
            color = colorResource(R.color.clouds),
            textAlign = TextAlign.Center,
        )
    }
}