package com.tbse.wnsw.models

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2017
 */
data class AccessPoint(
    val BSSID: String,
    var SSID: String,
    var capabilities: String,
    var frequency: Int,
    var level: Int,
    var strength: Int,
    var channel: Int,
    var latitude: Double,
    var longitude: Double
)
