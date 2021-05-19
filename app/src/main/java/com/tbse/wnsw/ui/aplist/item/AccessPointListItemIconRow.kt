package com.tbse.wnsw.ui.aplist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
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
fun AccessPointListItemIconRow(
    @PreviewParameter(
        provider = AccessPointPreviewProvider::class
    ) accessPoint: AccessPoint
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = "${accessPoint.strength}%",
            fontSize = 15.sp,
            color = colorResource(R.color.clouds),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Image(
            painter = painterResource(
                id = R.drawable.checkmarksmallgrey
            ),
            contentDescription = "not secure",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically)
        )
        Image(
            painter = painterResource(
                id = R.drawable.locksmallgrey
            ),
            contentDescription = "not configured",
            Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically)
        )
    }
}
