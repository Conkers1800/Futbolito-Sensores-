package com.navegacion.futbolitoo.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.conkers.futbolitoo.screens.InicioScreen
import com.conkers.futbolitoo.screens.JuegoScreen
import com.jesse.futbolitoo.navegacion.AppScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.InicioScreen.route) {
        composable(AppScreen.InicioScreen.route) {
            InicioScreen(navController)
        }
        composable(AppScreen.JuegoScreen.route) {
            JuegoScreen(navController)
        }
    }
}