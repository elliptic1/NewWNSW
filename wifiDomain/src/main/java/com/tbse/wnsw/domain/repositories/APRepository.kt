package com.tbse.wnsw.domain.repositories

import com.tbse.tbse.wifi.database.APDao
import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wnsw.domain.di.ModelMapper
import com.tbse.wnsw.domain.models.AccessPointDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by toddsmith on 12/11/21.
 * Copyright TBSE 2022
 */
class APRepository @Inject constructor(
    private val apDao: APDao,
    private val domainMapper: ModelMapper<AccessPoint, AccessPointDomain>,
    private val dbMapper: ModelMapper<AccessPointDomain, AccessPoint>
) {
    val getAllAps: Flow<List<AccessPointDomain>> = apDao.getAllAps()
        .map { list ->
            list.map {
                domainMapper(it)
            }
        }

    fun getAp(bssid: String): Flow<AccessPointDomain> {
        domainMapper
        return apDao.getAp(bssid)
            .map {
                domainMapper(it)
            }
    }

    suspend fun insertAp(ap: AccessPointDomain) {
        apDao.insertAP(
            dbMapper(ap)
        )
    }
}