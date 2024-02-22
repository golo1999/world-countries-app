package com.example.worldcountriesapp.retrofit

import com.example.worldcountriesapp.retrofit.country.Country
import com.example.worldcountriesapp.retrofit.country.CountryPresentation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApiService {
    @GET("all")
    suspend fun getCountries(): Response<List<CountryPresentation>>

    @GET("alpha/{countryCode}")
    suspend fun getCountryByCode(@Path("countryCode") name: String): Response<List<Country>>

    @GET("name/{countryName}")
    suspend fun getCountryByName(@Path("countryName") name: String): Response<List<Country>>
}