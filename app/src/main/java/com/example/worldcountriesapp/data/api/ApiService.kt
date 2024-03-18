package com.example.worldcountriesapp.data.api

import com.example.worldcountriesapp.data.entity.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("all")
    suspend fun getAllCountries(): Response<List<Country>>

    @GET("region/{region}")
    suspend fun getCountriesByRegion(@Path("region") region: String): Response<List<Country>>

    @GET("alpha/{countryCode}")
    suspend fun getCountryByCode(@Path("countryCode") code: String): Response<List<Country>>
}