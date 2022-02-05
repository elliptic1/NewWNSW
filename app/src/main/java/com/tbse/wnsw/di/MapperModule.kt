package com.tbse.wnsw.di

import com.tbse.wifi.support.ModelMapper
import com.tbse.wnsw.domain.models.AccessPointDomain
import com.tbse.wnsw.models.AccessPointUI
import com.tbse.wnsw.models.mapper.AccessPointMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by toddsmith on 1/6/22.
 */
@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideAccessPointDomainToUIMapper(
        mapper: AccessPointMapper,
    ): ModelMapper<AccessPointDomain, AccessPointUI> {
        return mapper
    }

}