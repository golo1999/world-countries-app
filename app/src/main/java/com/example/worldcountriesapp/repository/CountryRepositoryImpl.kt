package com.example.worldcountriesapp.repository

import com.example.worldcountriesapp.data.api.ApiService
import com.example.worldcountriesapp.data.dao.CountryDao
import com.example.worldcountriesapp.data.entity.Country
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val countryDao: CountryDao
) : CountryRepository {
    override suspend fun getAll(): List<Country> {
        val cachedCountries = countryDao.getAll()

        if (cachedCountries.isNotEmpty()) {
            return cachedCountries
        }

        val fetchedCountries = apiService.getAllCountries().body()?.sortedBy { it.name.common } ?: emptyList()

        if (fetchedCountries.isNotEmpty()) {
            countryDao.upsertAll(fetchedCountries)
        }

        return fetchedCountries
    }

    override suspend fun getByCode(countryCode: String): Country? {
        return apiService.getCountryByCode(countryCode).body()?.get(0)
    }

    override suspend fun getByRegion(region: String): List<Country> {
        val cachedCountries = countryDao.getByRegion(region)

        if (cachedCountries.isNotEmpty()) {
            return cachedCountries
        }

        return apiService.getCountriesByRegion(region).body()?.sortedBy { it.name.common } ?: emptyList()
    }
}