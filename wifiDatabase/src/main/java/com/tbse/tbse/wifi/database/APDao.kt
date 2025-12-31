package com.tbse.tbse.wifi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Created by toddsmith on 12/7/21.
 * Copyright TBSE 2022
 */
@Dao
interface APDao {

    @Query("SELECT * FROM ap_table ORDER BY CASE WHEN ssid = '' THEN 1 ELSE 0 END, ssid ASC")
    fun getAllAps(): Flow<List<AccessPoint>>

    @Query("SELECT * FROM ap_table WHERE bssid=:bssid LIMIT 1")
    fun getAp(bssid: String): Flow<AccessPoint>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAP(ap: AccessPoint)

    @Update
    suspend fun updateAP(ap: AccessPoint)

    @Delete
    suspend fun deleteAP(ap: AccessPoint)

    @Query("DELETE FROM ap_table")
    suspend fun deleteAllAPs()

    @Query("UPDATE ap_table SET isSuggested = :isFavorite WHERE bssid = :bssid")
    suspend fun updateFavorite(bssid: String, isFavorite: Boolean)

    @Query("SELECT * FROM ap_table WHERE isSuggested = 1 ORDER BY level DESC")
    fun getFavoritesByStrength(): Flow<List<AccessPoint>>

}
