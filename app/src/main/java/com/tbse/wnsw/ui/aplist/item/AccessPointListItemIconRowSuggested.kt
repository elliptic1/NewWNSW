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
fun AccessPointListItemIconRowSuggested(
    @PreviewParameter(
        provider = BooleanPreviewProvider::class
    ) suggested: Boolean,
) {
    Image(
        painter = painterResource(
            id = if (suggested) {
                R.drawable.checkmarksmall
            } else {
                R.drawable.checkmarksmallgrey
            }
        ),
        contentDescription = if (suggested) {
            "suggested"
        } else {
            "not suggested"
        },
        modifier = Modifier
            .size(20.dp)
    )
}
