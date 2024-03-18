package com.example.worldcountriesapp.data.entity

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
@Immutable
data class Country(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: Name,
    val tld: List<String>?,
    val independent: Boolean,
    val status: String,
    val currencies: Map<String, Currency>?,
    @ColumnInfo(name = "alt_spellings") val altSpellings: List<String>,
    val capital: List<String>?,
    val region: String,
    val subregion: String?,
    val languages: Map<String, String>?,
    val translations: Map<String, Translation>,
    @ColumnInfo(name = "coordinates") val latlng: List<Double>,
    val landlocked: Boolean,
    val borders: List<String>?,
    val area: Double,
    val demonyms: Map<String, Demonym>?,
    val maps: Map<String, String>,
    val population: Long,
    val car: Driving,
    val timezones: List<String>,
    val continents: List<String>,
    val flags: Map<String, String>,
    @ColumnInfo(name = "coat_of_arms") val coatOfArms: Map<String, String>,
    @ColumnInfo(name = "start_of_week") val startOfWeek: String,
    @ColumnInfo(name = "capital_info") val capitalInfo: Map<String, List<Double>>
)
