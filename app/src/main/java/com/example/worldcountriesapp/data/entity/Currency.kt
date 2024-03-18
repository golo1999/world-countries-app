package com.example.worldcountriesapp.data.entity

import androidx.compose.runtime.Immutable

@Immutable
data class Currency(
    val name: String,
    val symbol: String?
)
