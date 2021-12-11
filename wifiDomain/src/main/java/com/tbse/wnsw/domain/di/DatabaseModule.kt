package com.tbse.wnsw.domain.di

import android.content.Context
import androidx.room.Room
import com.tbse.tbse.wifi.database.APDatabase
import com.tbse.tbse.wifi.database.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by toddsmith on 12/11/21.
 * Copyright TBSE 2022
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        APDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: APDatabase) = database.apDao()

}