package com.example.barbell.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            // We will build HomeScreen next!
            // HomeScreen(
            //     onNavigateToLog = { exerciseId ->
            //         navController.navigate("log_workout/$exerciseId")
            //     }
            // )
        }
        composable("log_workout/{exerciseId}") { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")
            // We will build LogWorkoutScreen later!
            // LogWorkoutScreen(
            //     exerciseId = exerciseId,
            //     onNavigateBack = { navController.popBackStack() },
            //     onNavigateToCamera = { navController.navigate("camera") }
            // )
        }
        composable("camera") {
            // Camera screen goes here
        }
    }
}