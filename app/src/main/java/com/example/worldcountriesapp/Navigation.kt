package com.example.worldcountriesapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.worldcountriesapp.composable.CountryInfoScreen
import com.example.worldcountriesapp.composable.HomeScreen

@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.CountryInfoScreen.route + "/{name}",
            arguments = listOf(navArgument("name") {
                type = NavType.StringType
                nullable = false
            })
        ) { navBackStackEntry ->
            CountryInfoScreen(
                name = navBackStackEntry.arguments?.getString("name")!!,
                navController,
                viewModel
            )
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                navController,
                viewModel
            )
        }
    }
}