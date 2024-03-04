package com.example.worldcountriesapp.data.model.country

import androidx.compose.runtime.Immutable

@Immutable
data class Translation(
    val common: String,
    val official: String
)
