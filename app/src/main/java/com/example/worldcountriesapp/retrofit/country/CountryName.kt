package com.example.worldcountriesapp.retrofit.country

data class CountryName(
    val common: String,
    val official: String,
    val nativeName: Map<String, CountrySimpleName>?
)
