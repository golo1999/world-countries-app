package com.example.worldcountriesapp.data.model.country

import androidx.compose.runtime.Immutable

@Immutable
data class Driving(
    val signs: List<String>,
    val side: String
)
