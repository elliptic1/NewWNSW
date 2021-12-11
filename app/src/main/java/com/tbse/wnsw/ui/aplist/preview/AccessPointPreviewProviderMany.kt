package com.tbse.wnsw.ui.aplist.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.tbse.wnsw.models.AccessPointUI

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2022
 */
class AccessPointPreviewProviderMany : PreviewParameterProvider<AccessPointUI> {
    override val values: Sequence<AccessPointUI>
        get() = (0..20).map {
            AccessPointPreviewProvider().values.iterator().next()
        }.asSequence()
}

