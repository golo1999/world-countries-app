package com.example.worldcountriesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.worldcountriesapp.data.dao.CountryDao
import com.example.worldcountriesapp.data.dao.RegionDao
import com.example.worldcountriesapp.data.entity.Converters
import com.example.worldcountriesapp.data.entity.Country

@Database(
    entities = [Country::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun regionDao(): RegionDao
}