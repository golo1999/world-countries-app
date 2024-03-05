package com.example.worldcountriesapp.data.model.country

import androidx.compose.runtime.Immutable

@Immutable
data class Currency(
    val name: String,
    val symbol: String?
)
