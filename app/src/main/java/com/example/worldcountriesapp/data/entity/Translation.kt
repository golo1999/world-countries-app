package com.example.worldcountriesapp.data.entity

import androidx.compose.runtime.Immutable

@Immutable
data class Translation(
    val common: String,
    val official: String
)
