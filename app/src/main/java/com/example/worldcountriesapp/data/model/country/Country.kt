package com.example.worldcountriesapp.data.model.country

import androidx.compose.runtime.Immutable

@Immutable
data class Country(
    val name: Name,
    val tld: List<String>?,
    val independent: Boolean,
    val status: String,
    val currencies: Map<String, Currency>?,
    val altSpellings: List<String>,
    val capital: List<String>?,
    val region: String,
    val subregion: String?,
    val languages: Map<String, String>?,
    val translations: Map<String, Translation>,
    val latlng: List<Double>,
    val landlocked: Boolean,
    val borders: List<String>?,
    val area: Double,
    val demonyms: Map<String, Demonym>?,
    val flag: String,
    val maps: Map<String, String>,
    val population: Long,
    val car: Driving,
    val timezones: List<String>,
    val continents: List<String>,
    val flags: Map<String, String>,
    val coatOfArms: Map<String, String>,
    val startOfWeek: String,
    val capitalInfo: Map<String, List<Double>>
)
