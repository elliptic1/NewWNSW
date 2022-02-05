package com.tbse.wnsw.system.di

import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wifi.support.ModelMapper
import com.tbse.wnsw.system.mapper.ScanResultMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by toddsmith on 1/23/22.
 */
@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    @Singleton
    @Provides
    fun provideScanResultMapper(
        wifiManager: WifiManager,
    ): ModelMapper<ScanResult, AccessPoint> {
        return ScanResultMapper(wifiManager)
    }

}