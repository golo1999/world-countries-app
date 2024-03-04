package com.example.worldcountriesapp.repository

import com.example.worldcountriesapp.data.api.ApiService
import com.example.worldcountriesapp.data.model.country.Country
import com.example.worldcountriesapp.data.model.country.CountryPresentation
import retrofit2.Response
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CountryRepository {
    override suspend fun getAllCountries(): Response<List<CountryPresentation>> {
        return apiService.getAllCountries()
    }

    override suspend fun getCountryByCode(countryCode: String): Response<List<Country>> {
        return apiService.getCountryByCode(countryCode)
    }
}