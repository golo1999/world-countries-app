package com.example.worldcountriesapp.retrofit.country

data class CountryPresentation(
    val name: CountryName,
    val capital: List<String>?,
    val region: String,
    val population: Long,
    val flags: Map<String, String>
)
