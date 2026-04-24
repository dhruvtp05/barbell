package com.example.barbell.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.barbell.ui.screens.exercise_progress.ExerciseProgressScreen
import com.example.barbell.ui.screens.home.HomeScreen
import com.example.barbell.ui.screens.log_workout.LogWorkoutScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
             HomeScreen(
                 onNavigateToExerciseProgress = { exerciseId ->
                     navController.navigate("exercise_progress/$exerciseId")
                 }
             )
        }
        composable("exercise_progress/{exerciseId}") { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")
            ExerciseProgressScreen(
                exerciseId = exerciseId,
                onNavigateBack = { navController.popBackStack() },
                onAddEntry = { selectedExerciseId ->
                    navController.navigate("log_workout/$selectedExerciseId")
                }
            )
        }
        composable("log_workout/{exerciseId}") { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")
             LogWorkoutScreen(
                 exerciseId = exerciseId,
                 onNavigateBack = { navController.popBackStack() }
             )
        }
    }
}