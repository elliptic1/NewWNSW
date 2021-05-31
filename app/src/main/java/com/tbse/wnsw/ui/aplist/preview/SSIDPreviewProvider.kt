package com.tbse.wnsw.ui.aplist.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2017
 */
class SSIDPreviewProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = sequenceOf(
            RandomString().random(),
        )
}

