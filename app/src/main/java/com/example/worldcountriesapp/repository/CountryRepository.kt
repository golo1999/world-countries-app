package com.example.worldcountriesapp.repository

import com.example.worldcountriesapp.data.entity.Country

interface CountryRepository {
    suspend fun getAll(): List<Country>
    suspend fun getByCode(countryCode: String): Country?
    suspend fun getByRegion(region: String): List<Country>
}