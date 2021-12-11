package com.tbse.wnsw.ui.aplist.item

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.tbse.wnsw.R
import com.tbse.wnsw.ui.aplist.preview.StrengthPreviewProvider

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2022
 */
@Preview
@Composable
fun AccessPointListItemIconRowStrength(
    @PreviewParameter(
        provider = StrengthPreviewProvider::class
    ) strength: Int,
) {
    Text(
        text = "$strength%",
        fontSize = 15.sp,
        color = colorResource(R.color.clouds),
        textAlign = TextAlign.Center
    )
}
