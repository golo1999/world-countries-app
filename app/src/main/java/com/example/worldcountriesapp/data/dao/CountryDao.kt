package com.example.worldcountriesapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.worldcountriesapp.data.entity.Country

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    suspend fun getAll(): List<Country>

    @Query("SELECT * FROM country WHERE region = :region")
    suspend fun getByRegion(region: String): List<Country>

    @Upsert
    suspend fun upsertAll(countries: List<Country>)
}