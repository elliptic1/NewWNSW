package com.tbse.wnsw.ui.aplist

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tbse.wnsw.R

/**
 * Created by toddsmith on 12/13/21.
 * Copyright TBSE 2022
 */
@Composable
fun APListFAB(
    setLastLoad: () -> Unit,
) {
    FloatingActionButton(
        backgroundColor = Color.Green,
        onClick = setLastLoad
    ) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = stringResource(id = R.string.refresh),
            tint = colorResource(id = R.color.darkgrey)
        )
    }
}

@Preview
@Composable
fun PreviewAPListFAB() {
    APListFAB {}
}
