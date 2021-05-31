package com.tbse.wnsw.ui.need_permissions.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2017
 */
class OnClickLambdaPreviewProvider : PreviewParameterProvider<() -> Unit> {
    override val values: Sequence<() -> Unit>
        get() = sequenceOf(
            {}
        )
}

