package com.example.worldcountriesapp.data.api

import com.example.worldcountriesapp.data.model.country.Country
import com.example.worldcountriesapp.data.model.country.CountryPresentation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("all")
    suspend fun getAllCountries(): Response<List<CountryPresentation>>

    @GET("alpha/{countryCode}")
    suspend fun getCountryByCode(@Path("countryCode") code: String): Response<List<Country>>
}