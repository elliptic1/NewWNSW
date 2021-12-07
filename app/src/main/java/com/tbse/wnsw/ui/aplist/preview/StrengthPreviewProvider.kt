package com.tbse.wnsw.ui.aplist.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlin.random.Random

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2022
 */
class StrengthPreviewProvider : PreviewParameterProvider<Int> {
    override val values: Sequence<Int>
        get() = sequenceOf(
            Random.nextInt(100),
        )
}

