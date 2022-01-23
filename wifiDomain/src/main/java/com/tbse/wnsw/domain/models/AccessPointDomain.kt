package com.tbse.wnsw.domain.models

/**
 * Created by toddsmith on 12/11/21.
 * Copyright TBSE 2022
 */
data class AccessPointDomain(
    val BSSID: String,
    val SSID: String,
    val capabilities: String,
    val frequency: Int,
    val level: Int,
    val strength: Int,
    val channel: Int,
    val latitude: Double,
    val longitude: Double,
    val isSuggested: Boolean,
)
