package com.tbse.wnsw.ui.aplist.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.tbse.wnsw.R
import com.tbse.wnsw.ui.aplist.preview.BooleanPreviewProvider

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2022
 */
@Preview
@Composable
fun AccessPointListItemIconRowSecured(
    @PreviewParameter(
        provider = BooleanPreviewProvider::class
    ) secured: Boolean,
) {
    Image(
        painter = painterResource(
            id = if (secured) {
                R.drawable.locksmall
            } else {
                R.drawable.locksmallgrey
            }
        ),
        contentDescription = if (secured) {
            "secure"
        } else {
            "not secure"
        },
        Modifier
            .size(20.dp)
    )
}
