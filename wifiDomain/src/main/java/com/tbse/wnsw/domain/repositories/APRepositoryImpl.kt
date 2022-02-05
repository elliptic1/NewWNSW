package com.tbse.wnsw.domain.repositories

import com.tbse.tbse.wifi.database.APDao
import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wifi.support.ModelMapper
import com.tbse.wnsw.domain.models.AccessPointDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by toddsmith on 12/11/21.
 * Copyright TBSE 2022
 */
class APRepositoryImpl @Inject constructor(
    private val apDao: APDao,
    private val domainMapper: ModelMapper<AccessPoint, AccessPointDomain>,
    private val dbMapper: ModelMapper<AccessPointDomain, AccessPoint>,
) : APRepository {
    override fun getAllAps(): Flow<List<AccessPointDomain>> = apDao.getAllAps()
        .map { list ->
            list.map {
                domainMapper(it)
            }
        }

    override fun getAp(bssid: String): Flow<AccessPointDomain> {
        domainMapper
        return apDao.getAp(bssid)
            .map {
                domainMapper(it)
            }
    }

    override suspend fun insertAp(ap: AccessPointDomain) {
        apDao.insertAP(
            dbMapper(ap)
        )
    }
}