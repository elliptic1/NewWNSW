package com.tbse.wnsw.ui.aplist.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlin.random.Random

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2017
 */
class BooleanPreviewProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(
            Random.nextBoolean()
        )
}

