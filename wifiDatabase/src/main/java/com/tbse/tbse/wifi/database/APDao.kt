package com.tbse.tbse.wifi.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Created by toddsmith on 12/7/21.
 * Copyright TBSE 2022
 */
@Dao
interface APDao {

    @Query("SELECT * FROM ap_table ORDER BY ssid ASC")
    fun getAllAps(): Flow<List<AccessPoint>>

    @Query("SELECT * FROM ap_table WHERE bssid=:bssid")
    fun getAp(bssid: String): Flow<AccessPoint>

}
