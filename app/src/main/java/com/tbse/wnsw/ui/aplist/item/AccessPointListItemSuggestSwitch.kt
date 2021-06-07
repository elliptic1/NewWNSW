/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2017
 */
package com.tbse.wnsw.ui.aplist.item

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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.tbse.wnsw.models.AccessPoint
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProvider

@Preview
@Composable
fun AccessPointListItemSuggestSwitch(
    @PreviewParameter(
        provider = AccessPointPreviewProvider::class
    ) accessPoint: AccessPoint,
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
            mutableStateOf(false)
        }
        Switch(
            enabled = true,
            checked = switchState.value,
            colors = SwitchDefaults.colors(),
            onCheckedChange = {
                switchState.value = it
            },
            modifier = Modifier
                .width(40.dp)
                .align(Alignment.Center)
        )
    }
}
