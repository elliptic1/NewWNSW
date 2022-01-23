package com.tbse.wnsw.domain.di

import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wnsw.domain.models.AccessPointDomain

/**
 * Created by toddsmith on 1/23/22.
 */
class AccessPointDomainMapper: ModelMapper<AccessPoint, AccessPointDomain> {
    override operator fun invoke(input: AccessPoint): AccessPointDomain {
        return with(input) {
            AccessPointDomain(
                BSSID = bssid,
                SSID = ssid,
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