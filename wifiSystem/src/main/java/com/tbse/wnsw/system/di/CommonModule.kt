package com.tbse.wnsw.system.di

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by toddsmith on 1/23/22.
 */
@Module
@InstallIn(SingletonComponent::class)
class CommonModule() {
    @Provides
    fun provideWifiManager(
        @ApplicationContext appContext: Context,
    ): WifiManager {
        return appContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
    }
}