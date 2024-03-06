package com.example.worldcountriesapp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.worldcountriesapp.ui.navigation.Screen
import com.example.worldcountriesapp.ui.screen.capitalcitiesquiz.CapitalCitiesQuizScreen
import com.example.worldcountriesapp.ui.screen.coatofarmsquiz.CoatOfArmsQuizScreen
import com.example.worldcountriesapp.ui.screen.countryinfo.CountryInfoScreen
import com.example.worldcountriesapp.ui.screen.countrylist.CountryListScreen
import com.example.worldcountriesapp.ui.screen.flagsquiz.FlagsQuizScreen
import com.example.worldcountriesapp.ui.screen.home.HomeScreen
import com.example.worldcountriesapp.viewmodel.CountryInfoScreenViewModel
import com.example.worldcountriesapp.viewmodel.CountryListScreenViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.CapitalCitiesQuizScreen.route) {
            CapitalCitiesQuizScreen()
        }
        composable(route = Screen.CoatOfArmsQuizScreen.route) {
            CoatOfArmsQuizScreen()
        }
        composable(
            route = Screen.CountryInfoScreen.route + "/{code}",
            arguments = listOf(navArgument("code") {
                type = NavType.StringType
                nullable = false
            })
        ) { navBackStackEntry ->
            val countryInfoScreenViewModel = hiltViewModel<CountryInfoScreenViewModel>()
            val countryInfoScreenState by countryInfoScreenViewModel.state.collectAsState()

            CountryInfoScreen(
                code = navBackStackEntry.arguments?.getString("code")!!,
                state = countryInfoScreenState,
                onBackClick = { navController.popBackStack() },
                onBorderCountryClick = { countryCode ->
                    navController.navigate("${Screen.CountryInfoScreen.route}/${countryCode}")
                },
                onEvent = countryInfoScreenViewModel::onEvent
            )
        }
        composable(route = Screen.CountryListScreen.route) {
            val countryListScreenViewModel = hiltViewModel<CountryListScreenViewModel>()
            val countryListScreenState by countryListScreenViewModel.state.collectAsState()

            CountryListScreen(
                state = countryListScreenState,
                onEvent = countryListScreenViewModel::onEvent,
                onCardClick = { countryCode ->
                    navController.navigate("${Screen.CountryInfoScreen.route}/${countryCode}")
                }
            )
        }
        composable(route = Screen.FlagsQuizScreen.route) {
            FlagsQuizScreen()
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                onCapitalCitiesQuizClick = { navController.navigate(Screen.CapitalCitiesQuizScreen.route) },
                onCoatOfArmsQuizClick = { navController.navigate(Screen.CoatOfArmsQuizScreen.route) },
                onCountryListClick = { navController.navigate(Screen.CountryListScreen.route) },
                onFlagsQuizClick = { navController.navigate(Screen.FlagsQuizScreen.route) }
            )
        }
    }
}