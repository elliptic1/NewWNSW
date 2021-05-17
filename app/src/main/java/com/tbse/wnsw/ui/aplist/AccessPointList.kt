package com.tbse.wnsw.ui.aplist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tbse.wnsw.models.AccessPoint
import com.tbse.wnsw.ui.aplist.item.AccessPointListItem

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2017
 */
@Composable
fun AccessPointList(
    modifier: Modifier = Modifier,
    itemViewStates: List<AccessPoint>
) {
    LazyColumn(modifier = modifier) {
        items(itemViewStates) { data ->
            AccessPointListItem(accessPoint = data)
        }
    }
}