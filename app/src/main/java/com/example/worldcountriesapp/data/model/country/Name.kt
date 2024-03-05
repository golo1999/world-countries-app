package com.example.worldcountriesapp.data.model.country

import androidx.compose.runtime.Immutable

@Immutable
data class Name(
    val common: String,
    val official: String,
    val nativeName: Map<String, SimpleName>?
)
