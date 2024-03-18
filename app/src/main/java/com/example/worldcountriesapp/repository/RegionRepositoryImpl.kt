package com.example.worldcountriesapp.repository

import com.example.worldcountriesapp.data.api.ApiService
import com.example.worldcountriesapp.data.dao.CountryDao
import com.example.worldcountriesapp.data.dao.RegionDao
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val countryDao: CountryDao,
    private val regionDao: RegionDao
) : RegionRepository {
    override suspend fun getAll(): List<String> {
        val cachedRegions = regionDao.getAll()

        if (cachedRegions.isNotEmpty()) {
            return cachedRegions.sortedDescending().plus("All").reversed()
        }

        val cachedCountries = countryDao.getAll()

        if (cachedCountries.isNotEmpty()) {
            return cachedCountries.asSequence().distinctBy { it.region }.map { it.region }.sortedDescending().toList()
                .plus("All").reversed()
        }

        return apiService.getAllCountries().body()?.asSequence()?.distinctBy { it.region }?.map { it.region }
            ?.sortedDescending()?.toList()?.plus("All")?.reversed()
            ?: emptyList()
    }
}