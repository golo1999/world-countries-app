package com.example.worldcountriesapp.mock

import com.example.worldcountriesapp.data.model.country.Name
import com.example.worldcountriesapp.data.model.country.CountryPresentation
import com.example.worldcountriesapp.data.model.country.SimpleName

object MockedData {
    private val name = Name(
        common = "Romania",
        official = "Romania",
        nativeName = mapOf(
            "ron" to SimpleName(
                common = "România",
                official = "România"
            )
        )
    )
    val countryPresentation = CountryPresentation(
        name = name,
        altSpellings = listOf("RO", "Rumania", "Roumania", "România"),
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