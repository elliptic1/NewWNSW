package com.tbse.wnsw.domain.di

import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wnsw.domain.models.AccessPointDomain

/**
 * Created by toddsmith on 1/23/22.
 */
class AccessPointDatabaseMapper :
    com.tbse.wifi.support.ModelMapper<AccessPointDomain, AccessPoint> {
    override operator fun invoke(input: AccessPointDomain): AccessPoint {
        return with(input) {
            AccessPoint(
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