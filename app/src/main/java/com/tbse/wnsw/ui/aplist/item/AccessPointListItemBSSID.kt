package com.tbse.wnsw.ui.aplist.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tbse.wnsw.R
import com.tbse.wnsw.ui.aplist.preview.BSSIDPreviewProvider

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2022
 */
@Preview
@Composable
fun AccessPointListItemBSSID(
    @PreviewParameter(
        provider = BSSIDPreviewProvider::class
    ) bssid: String,
    channel: Int = 0
) {
    Box(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$bssid [ch. $channel]",
            fontSize = 15.sp,
            color = colorResource(R.color.clouds),
            textAlign = TextAlign.Center,
        )
    }
}