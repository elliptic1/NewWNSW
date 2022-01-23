package com.tbse.wnsw.models.transformers

import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import com.tbse.wnsw.models.AccessPointUI

/**
 * Created by toddsmith on 5/19/21.
 * Copyright TBSE 2022
 */
class ScanResultToAccessPointTransformer {
    operator fun invoke(scanResult: ScanResult, wifiManager: WifiManager): AccessPointUI {
        return AccessPointUI(
            BSSID = scanResult.BSSID,
            SSID = scanResult.SSID,
            capabilities = scanResult.capabilities,
            frequency = scanResult.frequency,
            level = scanResult.level,
            strength = (1.0 * wifiManager.calculateSignalLevel(scanResult.level) / wifiManager.maxSignalLevel * 100).toInt(),
            channel = scanResult.channelWidth,
            latitude = 0.0,
            longitude = 0.0,
            isSuggested = wifiManager.networkSuggestions.any { it.bssid.toString() == scanResult.BSSID }
        )
    }
}