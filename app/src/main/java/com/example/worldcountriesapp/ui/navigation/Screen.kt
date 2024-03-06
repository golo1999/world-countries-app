package com.example.worldcountriesapp.ui.navigation

sealed class Screen(val route: String) {
    data object CapitalCitiesQuizScreen : Screen("CapitalCitiesQuizScreen")
    data object CoatOfArmsQuizScreen : Screen("CoatOfArmsQuizScreen")
    data object CountryInfoScreen : Screen("CountryInfoScreen")
    data object CountryListScreen : Screen("CountryListScreen")
    data object FlagsQuizScreen : Screen("FlagsQuizScreen")
    data object HomeScreen : Screen("HomeScreen")
}