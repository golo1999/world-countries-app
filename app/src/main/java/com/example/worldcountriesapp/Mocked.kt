package com.example.worldcountriesapp

import com.example.worldcountriesapp.retrofit.country.CountryName
import com.example.worldcountriesapp.retrofit.country.CountryPresentation
import com.example.worldcountriesapp.retrofit.country.CountrySimpleName

object Mocked {
    private val countryName = CountryName(
        common = "Romania",
        official = "Romania",
        nativeName = mapOf(
            "ron" to CountrySimpleName(
                common = "România",
                official = "România"
            )
        )
    )
    val countryPresentation = CountryPresentation(
        name = countryName,
        capital = listOf("Bucharest"),
        region = "Europe",
        population = 19286123,
        flags = mapOf(
            "png" to "https://flagcdn.com/w320/ro.png",
            "svg" to "https://flagcdn.com/ro.svg",
            "alt" to "The flag of Romania is c…y blue, yellow and red."
        )
    )
}