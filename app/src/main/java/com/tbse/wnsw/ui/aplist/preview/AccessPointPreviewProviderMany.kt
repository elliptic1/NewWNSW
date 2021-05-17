package com.tbse.wnsw.ui.aplist.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.tbse.wnsw.models.AccessPoint
import kotlin.random.Random

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2017
 */
class AccessPointPreviewProviderMany : PreviewParameterProvider<AccessPoint> {
    override val values: Sequence<AccessPoint>
        get() = (0..20).map {
            AccessPointPreviewProvider().values.iterator().next()
        }.asSequence()
}

