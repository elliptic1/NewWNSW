package com.tbse.wnsw.domain.di

import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wnsw.domain.models.AccessPointDomain

/**
 * Created by toddsmith on 1/23/22.
 */
class AccessPointDatabaseMapper :
    ModelMapper<AccessPointDomain, AccessPoint> {
    override operator fun invoke(input: AccessPointDomain): AccessPoint {
        return with(input) {
            AccessPoint(
                id = -1,
                bssid = BSSID,
                ssid = SSID,
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