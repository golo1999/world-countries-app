package com.example.worldcountriesapp.data.model.country

import androidx.compose.runtime.Immutable

@Immutable
data class CountryPresentation(
    val name: Name,
    val altSpellings: List<String>,
    val capital: List<String>?,
    val region: String,
    val population: Long,
    val flags: Map<String, String>
)
