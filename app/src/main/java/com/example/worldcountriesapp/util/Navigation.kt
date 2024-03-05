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
import com.example.worldcountriesapp.ui.screen.countrylist.CountryListScreen
import com.example.worldcountriesapp.ui.screen.info.CountryInfoScreen
import com.example.worldcountriesapp.viewmodel.CountryInfoScreenViewModel
import com.example.worldcountriesapp.viewmodel.CountryListScreenViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Screen.CountryListScreen.route
    ) {
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
    }
}