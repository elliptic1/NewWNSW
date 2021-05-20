package com.tbse.wnsw.models.transformers

import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import com.tbse.wnsw.models.AccessPoint

/**
 * Created by toddsmith on 5/19/21.
 * Copyright TBSE 2017
 */
class ScanResultToAccessPointTransformer {
    operator fun invoke(scanResult: ScanResult, wifiManager: WifiManager): AccessPoint {
        return AccessPoint(
            BSSID = scanResult.BSSID,
            SSID = scanResult.SSID,
            capabilities = scanResult.capabilities,
            frequency = scanResult.frequency,
            level = scanResult.level,
            strength = wifiManager.calculateSignalLevel(scanResult.level),
            channel = scanResult.channelWidth,
            latitude = 0.0,
            longitude = 0.0
        )
    }
}