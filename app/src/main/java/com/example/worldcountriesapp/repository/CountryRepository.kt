package com.example.worldcountriesapp.repository

import com.example.worldcountriesapp.data.model.country.Country
import com.example.worldcountriesapp.data.model.country.CountryPresentation
import retrofit2.Response

interface CountryRepository {
    suspend fun getAllCountries(): Response<List<CountryPresentation>>
    suspend fun getCountryByCode(countryCode: String): Response<List<Country>>
}