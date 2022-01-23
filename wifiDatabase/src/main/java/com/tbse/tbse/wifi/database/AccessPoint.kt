package com.tbse.tbse.wifi.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tbse.tbse.wifi.database.Constants.AP_TABLE_NAME

/**
 * Created by toddsmith on 11/8/21.
 * Copyright TBSE 2022
 */
@Entity(tableName = AP_TABLE_NAME)
data class AccessPoint(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val bssid: String,
    val ssid: String,
    val capabilities: String,
    val frequency: Int,
    val level: Int,
    val strength: Int,
    val channel: Int,
    val latitude: Double,
    val longitude: Double,
    val isSuggested: Boolean,
)
