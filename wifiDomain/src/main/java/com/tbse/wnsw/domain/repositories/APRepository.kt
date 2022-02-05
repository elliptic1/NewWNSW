package com.tbse.wnsw.domain.repositories

import com.tbse.wnsw.domain.models.AccessPointDomain
import kotlinx.coroutines.flow.Flow

/**
 * Created by toddsmith on 1/23/22.
 */
interface APRepository {
    fun getAllAps(): Flow<List<AccessPointDomain>>
    fun getAp(bssid: String): Flow<AccessPointDomain>
    suspend fun insertAp(ap: AccessPointDomain)
}