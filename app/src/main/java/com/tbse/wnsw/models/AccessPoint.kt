package com.tbse.wnsw.models

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2017
 */
data class AccessPoint(
    val BSSID: String,
    val SSID: String,
    val capabilities: String,
    val frequency: Int,
    val level: Int,
    val strength: Int,
    val channel: Int,
    val latitude: Double,
    val longitude: Double,
    val isSuggested: Boolean
)
