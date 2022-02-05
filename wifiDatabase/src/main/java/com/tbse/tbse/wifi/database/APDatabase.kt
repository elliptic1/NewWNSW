package com.tbse.tbse.wifi.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by toddsmith on 12/7/21.
 * Copyright TBSE 2022
 */
@Database(entities = [AccessPoint::class], version = 2, exportSchema = false)
abstract class APDatabase : RoomDatabase() {
    abstract fun apDao(): APDao
}