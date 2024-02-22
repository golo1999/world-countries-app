package com.example.worldcountriesapp.retrofit.country

data class Country(
    val name: CountryName,
    val tld: List<String>?,
    val independent: Boolean,
    val status: String,
    val currencies: Map<String, CountryCurrency>?,
    val altSpellings: List<String>,
    val capital: List<String>?,
    val region: String,
    val subregion: String?,
    val languages: Map<String, String>?,
    val translations: Map<String, CountryTranslation>,
    val latlng: List<Double>,
    val landlocked: Boolean,
    val borders: List<String>?,
    val area: Double,
    val demonyms: Map<String, CountryDemonym>?,
    val flag: String,
    val maps: Map<String, String>,
    val population: Long,
    val car: CountryDriving,
    val timezones: List<String>,
    val continents: List<String>,
    val flags: Map<String, String>,
    var startOfWeek: String,
    val capitalInfo: Map<String, List<Double>>
)
