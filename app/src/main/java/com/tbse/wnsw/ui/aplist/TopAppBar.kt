package com.tbse.wnsw.ui.aplist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.tbse.wnsw.R

/**
 * Created by toddsmith on 12/13/21.
 * Copyright TBSE 2022
 */
@Composable
fun ApListAppBar() {
    DefaultAppBar()
}

@Composable
fun DefaultAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = colorResource(id = R.color.clouds),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        backgroundColor = colorResource(id = R.color.darkgrey)
    )
}

@Composable
@Preview
fun PreviewDefaultAppBar() {
    DefaultAppBar()
}