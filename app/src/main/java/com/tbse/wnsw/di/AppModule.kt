package com.tbse.wnsw.di

import com.tbse.wnsw.domain.repositories.APRepository
import com.tbse.wnsw.domain.repositories.APRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by toddsmith on 1/23/22.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAPRepository(
        impl: APRepositoryImpl,
    ): APRepository {
        return impl
    }

}