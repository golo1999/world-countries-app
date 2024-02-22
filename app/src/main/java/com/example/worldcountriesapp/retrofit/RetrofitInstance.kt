package com.example.worldcountriesapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val baseUrl = "https://restcountries.com/v3.1/"
    val apiService: CountriesApiService by lazy {
        val retrofitInstance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitInstance.create(CountriesApiService::class.java)
    }
}