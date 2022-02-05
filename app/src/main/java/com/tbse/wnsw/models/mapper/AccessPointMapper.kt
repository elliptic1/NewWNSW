package com.tbse.wnsw.models.mapper

import com.tbse.wifi.support.ModelMapper
import com.tbse.wnsw.domain.models.AccessPointDomain
import com.tbse.wnsw.models.AccessPointUI
import javax.inject.Inject

/**
 * Created by toddsmith on 1/23/22.
 */
class AccessPointMapper @Inject constructor() : ModelMapper<AccessPointDomain, AccessPointUI> {
    override operator fun invoke(input: AccessPointDomain): AccessPointUI {
        return with(input) {
            AccessPointUI(
                BSSID = BSSID,
                SSID = SSID,
                capabilities = capabilities,
                frequency = frequency,
                level = level,
                strength = strength,
                channel = channel,
                latitude = latitude,
                longitude = longitude,
                isSuggested = isSuggested
            )
        }
    }
}