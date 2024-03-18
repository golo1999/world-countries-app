package com.example.worldcountriesapp.repository

interface RegionRepository {
    suspend fun getAll(): List<String>
}