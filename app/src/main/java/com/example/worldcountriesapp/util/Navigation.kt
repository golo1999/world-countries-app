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
import com.example.worldcountriesapp.ui.screen.home.HomeScreen
import com.example.worldcountriesapp.ui.screen.info.CountryInfoScreen
import com.example.worldcountriesapp.viewmodel.CountryInfoScreenViewModel
import com.example.worldcountriesapp.viewmodel.HomeScreenViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Screen.HomeScreen.route
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
                onEvent = countryInfoScreenViewModel::onEvent,
                onBackClick = { navController.popBackStack() },
                onBorderCountryClick = { countryCode ->
                    navController.navigate("${Screen.CountryInfoScreen.route}/${countryCode}")
                }
            )
        }
        composable(route = Screen.HomeScreen.route) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            val homeScreenState by homeScreenViewModel.state.collectAsState()

            HomeScreen(
                state = homeScreenState,
                onEvent = homeScreenViewModel::onEvent,
                onCardClick = { countryCode ->
                    navController.navigate("${Screen.CountryInfoScreen.route}/${countryCode}")
                }
            )
        }
    }
}