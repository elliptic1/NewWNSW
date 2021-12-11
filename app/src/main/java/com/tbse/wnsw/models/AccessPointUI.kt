package com.tbse.wnsw.models

import android.util.Log
import com.tbse.wnsw.TAG

/**
 * Created by toddsmith on 5/15/21.
 * Copyright TBSE 2022
 */
data class AccessPointUI(
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

fun AccessPointUI.log() {
    Log.d(TAG, "AP: $SSID, [$BSSID], ch $channel")
}
