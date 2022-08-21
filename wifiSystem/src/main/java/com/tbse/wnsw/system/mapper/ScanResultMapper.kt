package com.tbse.wnsw.system.mapper

import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wifi.support.ModelMapper
import javax.inject.Inject

/**
 * Created by toddsmith on 1/23/22.
 */
class ScanResultMapper @Inject constructor(
    private val wifiManager: WifiManager,
) : ModelMapper<ScanResult, AccessPoint> {
    override fun invoke(input: ScanResult): AccessPoint {
        return AccessPoint(
            bssid = input.BSSID,
            ssid = input.SSID,
            capabilities = input.capabilities,
            frequency = input.frequency,
            level = input.level,
            strength = signalStrengthMapper(input.level),
            channel = input.channelWidth,
            latitude = 0.0,
            longitude = 0.0,
            isSuggested = wifiManager.networkSuggestions.any { it.bssid.toString() == input.BSSID }
        )
    }

    private fun signalStrengthMapper(input: Int): Int {
        val signalLevelDouble = 1.0 * wifiManager.calculateSignalLevel(input)
        val levelRatio = signalLevelDouble / wifiManager.maxSignalLevel
        val percent = levelRatio * 100
        return percent.toInt()
    }
}