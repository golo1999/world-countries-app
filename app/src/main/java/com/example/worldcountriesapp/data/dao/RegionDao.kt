package com.example.worldcountriesapp.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RegionDao {
    @Query("SELECT DISTINCT region FROM country ORDER BY region ASC")
    suspend fun getAll(): List<String>
}