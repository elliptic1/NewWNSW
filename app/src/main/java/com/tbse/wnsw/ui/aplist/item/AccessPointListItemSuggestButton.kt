/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2017
 */
package com.tbse.wnsw.ui.aplist.item

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tbse.wnsw.R
import com.tbse.wnsw.models.AccessPoint
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProvider

@Preview
@Composable
fun AccessPointListItemSuggestButtonContent() {
    Text(
        "FAVORITE",
        fontSize = 20.sp,
        color = colorResource(R.color.darkgrey),
        modifier = Modifier
            .wrapContentWidth()
    )
}

@Preview
@Composable
fun AccessPointListItemSuggestButton(
    @PreviewParameter(
        provider = AccessPointPreviewProvider::class
    ) accessPoint: AccessPoint
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
        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = colorResource(id = R.color.clouds),
                contentColor = colorResource(id = R.color.green)
            ),
            onClick = {
                Log.d("wnsw", "it was clicked")
            },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Center)
        ) {
            AccessPointListItemSuggestButtonContent()
        }
    }
}
