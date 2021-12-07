package com.tbse.tbse.wifi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tbse.tbse.wifi.database.Constants.AP_TABLE

/**
 * Created by toddsmith on 11/8/21.
 * Copyright TBSE 2022
 */
@Entity(tableName = AP_TABLE)
data class AccessPoint(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "bssid") val bssid: String,
    val ssid: String
)
