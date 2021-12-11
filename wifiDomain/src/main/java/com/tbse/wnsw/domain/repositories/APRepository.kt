package com.tbse.wnsw.domain.repositories

import com.tbse.tbse.wifi.database.APDao
import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wnsw.domain.models.AccessPointDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by toddsmith on 12/11/21.
 * Copyright TBSE 2022
 */
class APRepository @Inject constructor(
    private val apDao: APDao,
) {
    val getAllAps: Flow<List<AccessPointDomain>> = apDao.getAllAps()
        .map { list ->
            list.map { ap ->
                AccessPointDomain(
                    ssid = ap.ssid,
                    bssid = ap.bssid
                )
            }
        }

    fun getAp(bssid: String): Flow<AccessPointDomain> {
        return apDao.getAp(bssid)
            .map {
                AccessPointDomain(
                    ssid = it.ssid,
                    bssid = it.bssid
                )
            }
    }

    suspend fun insertAp(ap: AccessPointDomain) {
        apDao.insertAP(
            AccessPoint(
                id = 1,
                bssid = ap.bssid,
                ssid = ap.ssid
            )
        )
    }
}