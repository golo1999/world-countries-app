package com.example.worldcountriesapp.data.entity

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo

@Immutable
data class Name(
    val common: String,
    val official: String,
    @ColumnInfo(name = "native_name") val nativeName: Map<String, SimpleName>?
)
