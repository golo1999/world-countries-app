package com.example.worldcountriesapp.ui.navigation

sealed class Screen(val route: String) {
    data object CountryInfoScreen : Screen("CountryInfoScreen")
    data object HomeScreen : Screen("HomeScreen")
}