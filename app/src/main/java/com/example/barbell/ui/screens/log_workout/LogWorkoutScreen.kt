package com.example.barbell.ui.screens.log_workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbell.viewmodel.LogWorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogWorkoutScreen(
    exerciseId: String?,
    onNavigateBack: () -> Unit,
    onNavigateToCamera: () -> Unit,
    viewModel: LogWorkoutViewModel = viewModel()
) {
    // These hold the numbers the user types in
    var weight by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log Workout") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Display which exercise we are logging (Just the ID for now until we hook up the DB)
            Text(
                text = "Logging Exercise ID: $exerciseId",
                style = MaterialTheme.typography.titleLarge
            )

            // Weight Input
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (lbs/kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Sets and Reps Inputs (Side by side)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = sets,
                    onValueChange = { sets = it },
                    label = { Text("Sets") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it },
                    label = { Text("Reps") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // The Camera Button
            Button(
                onClick = onNavigateToCamera,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Take Photo of Equipment")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // The Save Button
            Button(
                onClick = {
                    viewModel.saveWorkout(
                        exerciseId = exerciseId ?: "0",
                        weight = weight,
                        sets = sets,
                        reps = reps,
                        photoUri = null
                    )
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Save Workout")
            }
        }
    }
}