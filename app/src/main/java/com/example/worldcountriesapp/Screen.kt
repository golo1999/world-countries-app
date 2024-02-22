package com.example.worldcountriesapp

sealed class Screen(val route: String) {
    data object CountryInfoScreen : Screen("CountryInfoScreen")
    data object HomeScreen : Screen("HomeScreen")
}