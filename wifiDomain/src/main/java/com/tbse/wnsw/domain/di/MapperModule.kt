package com.tbse.wnsw.domain.di

import com.tbse.tbse.wifi.database.AccessPoint
import com.tbse.wnsw.domain.models.AccessPointDomain
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
    fun provideAccessPointDomainMapper(): ModelMapper<AccessPoint, AccessPointDomain> {
        return AccessPointDomainMapper()
    }

    @Singleton
    @Provides
    fun provideAccessPointMapper(): ModelMapper<AccessPointDomain, AccessPoint> {
        return AccessPointDatabaseMapper()
    }

}