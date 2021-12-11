package com.tbse.wnsw.ui.aplist.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.tbse.wnsw.models.AccessPointUI
import kotlin.random.Random

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2022
 */
class AccessPointPreviewProvider : PreviewParameterProvider<AccessPointUI> {
    override val values: Sequence<AccessPointUI>
        get() = sequenceOf(
                AccessPointUI(
                        BSSID = RandomMac.randomMacAddress,
                        SSID = RandomString().random(),
                        capabilities = RandomString().random(),
                        frequency = Random.nextInt(0, 100),
                        level = Random.nextInt(0, 10),
                        strength = Random.nextInt(0, 100),
                        channel = Random.nextInt(1, 8),
                        latitude = Random.nextDouble(-90.0, 90.0),
                        longitude = Random.nextDouble(-180.0, 180.0),
                        isSuggested = Random.nextBoolean(),
                )
        )
}

